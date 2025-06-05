package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.GameObjects.Player;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Rooms.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.AffineTransform;
import java.util.Optional;

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
 * MapPanel performs custom drawing of the game map.
 * It computes screen coordinates for each Room based on connection data,
 * draws rooms as circles with thicker, extended connection lines,
 * and uses a discrete paging system so that when the player's room reaches an edge,
 * the view shifts by one page to show more of the map.
 */
public class MapPanel extends JPanel {
    // Map of room IDs to corresponding Room instances.
    private Map<Integer, Room> rooms;
    // Holds the computed room coordinates (without dynamic scaling).
    private Map<Integer, Point> scaledRoomPositions = new LinkedHashMap<>();
    // Reference to the current player (to highlight their room).
    private Player player;

    // The current viewport offset in the world coordinate space.
    private int viewportOffsetX = 0;
    private int viewportOffsetY = 0;

    // Fixed scale factor – set it to 1.0 to preserve the raw positions,
    // or increase (or decrease) it to magnify (or shrink) the drawing.
    private double fixedScale = 1.0; // Adjust this value as needed.

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
        // Custom painting; no layout manager needed.
        setLayout(null);
    }

    /**
     * Clears previous coordinate mappings, computes new coordinates for all rooms
     * (using assignCoordinates that uses a fixed offset), then applies a fixed scale,
     * and finally adjusts the viewport offset in discrete “pages.”
     */
    public void refreshCoordinates() {
        scaledRoomPositions.clear();
        Room startingRoom = rooms.get(0);
        if (startingRoom != null) {
            // Begin with a fixed starting coordinate.
            assignCoordinates(startingRoom, new Point(50, 50), new HashSet<>());
        }

        // ----- Instead of dynamic scaling, we use a fixed scale factor.
        Map<Integer, Point> tempPositions = new LinkedHashMap<>();
        for (Map.Entry<Integer, Point> entry : scaledRoomPositions.entrySet()) {
            Point orig = entry.getValue();
            int newX = (int) (orig.x * fixedScale);
            int newY = (int) (orig.y * fixedScale);
            tempPositions.put(entry.getKey(), new Point(newX, newY));
        }
        scaledRoomPositions = tempPositions;

        // ----- Discrete Paging: Update the viewport offset if the player's room goes off-page.
        int panelWidth = getWidth() > 0 ? getWidth() : getPreferredSize().width;
        int panelHeight = getHeight() > 0 ? getHeight() : getPreferredSize().height;

        Room playerRoom = rooms.get(player.getPosition());
        if (playerRoom != null) {
            Point playerPos = scaledRoomPositions.get(playerRoom.getId());
            if (playerPos != null) {
                // Initialize the viewport offset on the first refresh.
                if (viewportOffsetX == 0 && viewportOffsetY == 0) {
                    viewportOffsetX = (playerPos.x / panelWidth) * panelWidth;
                    viewportOffsetY = (playerPos.y / panelHeight) * panelHeight;
                }

                // If player's room is left of the viewport, shift left one page.
                if (playerPos.x < viewportOffsetX) {
                    viewportOffsetX -= panelWidth;
                } else if (playerPos.x >= viewportOffsetX + panelWidth) {
                    viewportOffsetX += panelWidth;
                }
                // Similarly for vertical movement.
                if (playerPos.y < viewportOffsetY) {
                    viewportOffsetY -= panelHeight;
                } else if (playerPos.y >= viewportOffsetY + panelHeight) {
                    viewportOffsetY += panelHeight;
                }
            }
        }

        repaint();
    }

    /**
     * Recursively assigns coordinates to the given room and all its connected neighbors.
     * A fixed offset is used for each neighbor direction. The visited set prevents infinite loops.
     *
     * @param room    the current room.
     * @param pos     the position to assign to the current room.
     * @param visited a set of room IDs that have already been assigned coordinates.
     */
    private void assignCoordinates(Room room, Point pos, Set<Integer> visited) {
        if (visited.contains(room.getId())) return;
        scaledRoomPositions.put(room.getId(), pos);
        visited.add(room.getId());

        int offset = 100;  // The unscaled fixed offset.
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
        // Set up a thicker stroke for connecting lines.
        g2d.setStroke(new BasicStroke(4));

        // ----- Draw door connections -----
        for (Room room : rooms.values()) {
            Point rawPos = scaledRoomPositions.get(room.getId());
            if (rawPos == null) continue;
            // Adjust positions by the current viewport offset.
            Point pos = new Point(rawPos.x - viewportOffsetX, rawPos.y - viewportOffsetY);
            for (String direction : new String[] {"north", "south", "east", "west"}) {
                Optional<Connection> connectionOpt = room.getNeighbour(direction);
                if (!connectionOpt.isPresent()) continue;

                Room neighbour = connectionOpt.get().getDestination();
                Point neighbourRawPos = scaledRoomPositions.get(neighbour.getId());
                if (neighbourRawPos == null) continue;
                Point neighbourPos = new Point(neighbourRawPos.x - viewportOffsetX,
                        neighbourRawPos.y - viewportOffsetY);

                // Compute centers of the room circles.
                Point center = new Point(pos.x + roomRadius, pos.y + roomRadius);
                Point neighbourCenter = new Point(neighbourPos.x + roomRadius, neighbourPos.y + roomRadius);
                // Compute vector between centers.
                int dx = neighbourCenter.x - center.x;
                int dy = neighbourCenter.y - center.y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist == 0) dist = 1;
                // Extend connection line endpoints by 30% of the roomRadius.
                int extension = (int) (roomRadius * 0.3);
                int startX = center.x - (int) ((dx / dist) * extension);
                int startY = center.y - (int) ((dy / dist) * extension);
                int endX = neighbourCenter.x + (int) ((dx / dist) * extension);
                int endY = neighbourCenter.y + (int) ((dy / dist) * extension);

                // Set color according to connection type and state.
                Connection connection = connectionOpt.get();
                if (connection instanceof BossLockedDoorConnection) {
                    // Boss door connection: use a special color.
                    // You can customize these colors as desired.
                    BossLockedDoorConnection bldc = (BossLockedDoorConnection) connection;
                    // For example, if the boss door is unlocked, use CYAN; otherwise, use ORANGE.
                    g2d.setColor(bldc.canPass() ? Color.CYAN : Color.ORANGE);
                } else if (connection instanceof LockedDoorConnection) {
                    LockedDoorConnection ldc = (LockedDoorConnection) connection;
                    g2d.setColor(ldc.canPass() ? Color.BLACK : Color.RED);
                } else {
                    g2d.setColor(Color.BLACK);
                }

                g2d.drawLine(startX, startY, endX, endY);
            }
        }


        // ----- Draw rooms as circles with unique colors -----
        for (Room room : rooms.values()) {
            Point rawPos = scaledRoomPositions.get(room.getId());
            if (rawPos == null) continue;
            Point pos = new Point(rawPos.x - viewportOffsetX, rawPos.y - viewportOffsetY);
            Color fillColor;
            if (room instanceof StartingRoom) {
                fillColor = Color.GREEN;
            } else if (room instanceof PenultimateRoom) {
                fillColor = new Color(128, 0, 128);  // Purple.
            } else if (room instanceof BossRoom) {
                fillColor = Color.RED;
            } else {
                fillColor = Color.LIGHT_GRAY;
            }
            if (room.getId() == player.getPosition()) {
                fillColor = Color.ORANGE;
            }
            g2d.setColor(fillColor);
            g2d.fillOval(pos.x, pos.y, roomRadius * 2, roomRadius * 2);
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
