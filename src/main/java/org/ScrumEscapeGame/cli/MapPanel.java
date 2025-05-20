package org.ScrumEscapeGame.cli;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.Connection;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class MapPanel extends JPanel {
    private Map<Integer, Room> rooms;
    private Map<Integer, Point> roomPositions = new HashMap<>();
    private Player player;

    public MapPanel(Map<Integer, Room> rooms, Player player) {
        this.rooms = rooms;
        this.player = player;
        // Do not call computeRoomCoordinates here if rooms are not ready.
        setPreferredSize(new Dimension(400, 400));
    }

    /**
     * Call this after the room map is built to update positions.
     */
    public void refreshCoordinates() {
        roomPositions.clear();
        System.out.println("computing room coordinates...");
        Room startingRoom = rooms.get(0); // adjust if needed
        if (startingRoom != null) {
            assignCoordinates(startingRoom, new Point(50, 50), new HashSet<>());
        }
        repaint();
    }

    /**
     * Recursively assign coordinates to rooms.
     * <p>
     * For each room, try all fixed directions. When a neighbor is found and not yet assigned,
     * calculate its position by adding an offset and recursively assign its coordinates.
     */
    private void assignCoordinates(Room room, Point pos, Set<Integer> visited) {
        if (visited.contains(room.getId())) return;
        roomPositions.put(room.getId(), pos);
        visited.add(room.getId());
        int offset = 100;
        // Fixed set of directions to try.
        String[] directions = {"north", "south", "east", "west"};
        for (String direction : directions) {
            Optional<Connection> connectionOpt = room.getNeighbour(direction);
            if (connectionOpt.isPresent()) {
                // Use getDestination() to get the neighbor room.
                Room neighbour = connectionOpt.get().getDestination();
                System.out.printf("current room %d moving %s%n", room.getDisplayOrder(), direction);
                if (neighbour != null && !visited.contains(neighbour.getId())) {
                    Point offsetPoint = switch (direction) {
                        case "north" -> new Point(0, -offset);
                        case "south" -> new Point(0, offset);
                        case "east" -> new Point(offset, 0);
                        case "west" -> new Point(-offset, 0);
                        default -> null;
                    };
                    if (offsetPoint != null) {
                        Point neighbourPos = new Point(pos.x + offsetPoint.x, pos.y + offsetPoint.y);
                        assignCoordinates(neighbour, neighbourPos, visited);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("DEBUG: paintComponent activating...");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int roomRadius = 25;

        // Draw connections (doors)
        for (Room room : rooms.values()) {
            Point pos = roomPositions.get(room.getId());
            if (pos != null) {
                // Check each direction to see if a connection exists.
                for (String direction : new String[]{"north", "south", "east", "west"}) {
                    Optional<Connection> connectionOpt = room.getNeighbour(direction);
                    if (connectionOpt.isPresent()) {
                        Room neighbour = connectionOpt.get().getDestination();
                        Point neighbourPos = roomPositions.get(neighbour.getId());
                        if (neighbourPos != null) {
                            Connection connection = connectionOpt.get();
                            // For locked door connections, use canPass() to determine color.
                            if (connection instanceof org.ScrumEscapeGame.Rooms.LockedDoorConnection) {
                                org.ScrumEscapeGame.Rooms.LockedDoorConnection ldc =
                                        (org.ScrumEscapeGame.Rooms.LockedDoorConnection) connection;
                                if (ldc.canPass()) {
                                    // Door is unlocked so you can pass.
                                    g2d.setColor(Color.BLACK);
                                } else {
                                    // Door is locked—paint it red.
                                    g2d.setColor(Color.RED);
                                }
                            } else {
                                // For direct connections or other types, always paint black.
                                g2d.setColor(Color.BLACK);
                            }

                            g2d.drawLine(
                                    pos.x + roomRadius, pos.y + roomRadius,
                                    neighbourPos.x + roomRadius, neighbourPos.y + roomRadius
                            );
                        }
                    }
                }
            }
        }

        // Draw each room as a circle.
        for (Room room : rooms.values()) {
            Point pos = roomPositions.get(room.getId());
            if (pos != null) {
                // Highlight player's current room.
                if (room.getId() == player.getPosition()) {
                    g2d.setColor(Color.ORANGE);
                    g2d.fillOval(pos.x, pos.y, roomRadius * 2, roomRadius * 2);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillOval(pos.x, pos.y, roomRadius * 2, roomRadius * 2);
                }
                g2d.setColor(Color.BLACK);
                g2d.drawOval(pos.x, pos.y, roomRadius * 2, roomRadius * 2);

                // Draw the room's display order in the center of the circle.
                String text = String.valueOf(room.getDisplayOrder());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();
                int centerX = pos.x + roomRadius - textWidth / 2;
                int centerY = pos.y + roomRadius + textHeight / 2;
                g2d.drawString(text, centerX, centerY);
            }
        }
    }
}



