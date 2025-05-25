package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.Connection;
import org.ScrumEscapeGame.Rooms.LockedDoorConnection;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/*
    RELEVANTE LINKS:
    javax.swing - https://docs.oracle.com/en/java/javase/24/docs/api/java.desktop/javax/swing/package-summary.html
    javax.swing.Jcomponent - https://docs.oracle.com/en/java/javase/24/docs/api/java.desktop/javax/swing/JComponent.html

    java.awt - https://docs.oracle.com/en/java/javase/24/docs/api/java.desktop/java/awt/package-summary.html
    java.awt.Graphics2D - https://docs.oracle.com/en/java/javase/24/docs/api/java.desktop/java/awt/Graphics2D.html
    java.awt.FontMetrics - https://docs.oracle.com/en/java/javase/24/docs/api/java.desktop/java/awt/FontMetrics.html

    Kijk vooral naar method summary, "Modifier and Type" is de objecttype die de methode returned.
    Vraag een chatbot voor specifieke hulp.
 */

/**
 * MapPanel performs all custom drawing of the game map.
 * It computes screen coordinates for each Room (based on connection data)
 * and draws rooms as circles and door connections as lines.
 */
public class MapPanel extends JPanel {
    // Map of room IDs to corresponding Room instances.
    private Map<Integer, Room> rooms;
    // Holds the computed screen coordinates for each room.
    private Map<Integer, Point> roomPositions = new LinkedHashMap<>();
    // Reference to the current player (to highlight their room).
    private Player player;

    /**
     * Constructs a MapPanel.
     *
     * @param rooms  a map of room IDs to Room objects.
     * @param player the current player.
     */
    public MapPanel(Map<Integer, Room> rooms, Player player) {
        this.rooms = rooms;
        this.player = player;
        // Set a fixed preferred size.
        setPreferredSize(new Dimension(400, 400));
        // Set a background and a border so that the panel’s bounds are visible.
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // We perform custom painting; therefore, no layout manager is needed.
        setLayout(null);
    }

    /**
     * Clears the previous coordinate mappings, computes new coordinates for all rooms,
     * and then triggers a repaint.
     * <p>
     * Assumes that the starting room is stored with key 0.
     */
    public void refreshCoordinates() {
        roomPositions.clear();
        Room startingRoom = rooms.get(0);
        if (startingRoom != null) {
            // Starting room is positioned at (50,50)
            assignCoordinates(startingRoom, new Point(50, 50), new HashSet<>());
        }
        repaint();
    }

    /**
     * Recursively assigns coordinates to the given room and all its connected rooms.
     * A fixed offset is used for each direction. A visited set prevents infinite loops.
     *
     * @param room    the current room.
     * @param pos     the position to assign to the current room.
     * @param visited a set of room IDs already assigned positions.
     */
    private void assignCoordinates(Room room, Point pos, Set<Integer> visited) {
        if (visited.contains(room.getId())) return;
        roomPositions.put(room.getId(), pos);
        visited.add(room.getId());

        int offset = 100;
        String[] directions = {"north", "south", "east", "west"};
        for (String direction : directions) {
            Optional<Connection> connectionOpt = room.getNeighbour(direction);
            if (connectionOpt.isPresent()) {
                Room neighbour = connectionOpt.get().getDestination();
                if (neighbour != null && !visited.contains(neighbour.getId())) {
                    Point offsetPoint;
                    switch (direction) {
                        case "north":
                            offsetPoint = new Point(0, -offset);
                            break;
                        case "south":
                            offsetPoint = new Point(0, offset);
                            break;
                        case "east":
                            offsetPoint = new Point(offset, 0);
                            break;
                        case "west":
                            offsetPoint = new Point(-offset, 0);
                            break;
                        default:
                            offsetPoint = new Point(0, 0);
                    }
                    Point neighbourPos = new Point(pos.x + offsetPoint.x, pos.y + offsetPoint.y);
                    assignCoordinates(neighbour, neighbourPos, visited);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int roomRadius = 25;

        // ----- Draw door connections -----
        for (Room room : rooms.values()) {
            Point pos = roomPositions.get(room.getId());
            if (pos != null) {
                for (String direction : new String[]{"north", "south", "east", "west"}) {
                    Optional<Connection> connectionOpt = room.getNeighbour(direction);
                    if (connectionOpt.isPresent()) {
                        Room neighbour = connectionOpt.get().getDestination();
                        Point neighbourPos = roomPositions.get(neighbour.getId());
                        if (neighbourPos != null) {
                            Connection connection = connectionOpt.get();
                            if (connection instanceof LockedDoorConnection) {
                                LockedDoorConnection ldc = (LockedDoorConnection) connection;
                                // Use red if door is locked; black otherwise.
                                g2d.setColor(ldc.canPass() ? Color.BLACK : Color.RED);
                            } else {
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

        // ----- Draw rooms as circles -----
        for (Room room : rooms.values()) {
            Point pos = roomPositions.get(room.getId());
            if (pos != null) {
                // Highlight the player's position with orange; otherwise, use light gray.
                if (room.getId() == player.getPosition()) {
                    g2d.setColor(Color.ORANGE);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                g2d.fillOval(pos.x, pos.y, roomRadius * 2, roomRadius * 2);
                // Draw a thicker black border.
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(pos.x, pos.y, roomRadius * 2, roomRadius * 2);
                // Draw the room's display order centered within the circle.
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

    /**
     * Updates the room map and recalculates coordinates.
     *
     * @param rooms the new map of rooms.
     */
    public void setRooms(Map<Integer, Room> rooms) {
        this.rooms = rooms;
        refreshCoordinates();
    }
}






