package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.InventoryItemActionEvent;
import org.ScrumEscapeGame.AAEvents.InventoryItemClickedEvent;
import org.ScrumEscapeGame.AAEvents.InventoryItemHoveredEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Item;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InventoryPanel extends JPanel {
    private final DefaultListModel<Item> roomInventoryModel;
    private final JList<Item> roomInventoryList;
    private final DefaultListModel<Item> playerInventoryModel;
    private final JList<Item> playerInventoryList;
    private JTextArea messageArea;

    // Game context needed for updating lists and publishing events.
    private final GameContext context;

    public InventoryPanel(GameContext context, GameUIService uiService) {
        this.context = context;
        setLayout(new BorderLayout());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("I"), "toggleInventory");
        this.getActionMap().put("toggleInventory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiService.toggleInventoryPanel();
            }
        });


        // Set up the room inventory list.
        roomInventoryModel = new DefaultListModel<>();
        roomInventoryList = new JList<>(roomInventoryModel);
        roomInventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane roomScroll = new JScrollPane(roomInventoryList);
        roomScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Room Inventory",
                TitledBorder.LEFT, TitledBorder.TOP));

        // Set up the player inventory list.
        playerInventoryModel = new DefaultListModel<>();
        playerInventoryList = new JList<>(playerInventoryModel);
        playerInventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane playerScroll = new JScrollPane(playerInventoryList);
        playerScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Player Inventory",
                TitledBorder.LEFT, TitledBorder.TOP));

        // Use a JSplitPane to display both inventories side by side.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, roomScroll, playerScroll);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // Add mouse listeners for room inventory.
        roomInventoryList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = roomInventoryList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Item item = roomInventoryModel.getElementAt(index);
                    context.getEventPublisher().publish(
                            new InventoryItemHoveredEvent(item.getId(), item.getName(), item.getDescription(), "room")
                    );
                }
            }
        });

        roomInventoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = roomInventoryList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Item item = roomInventoryModel.getElementAt(index);
                    context.getEventPublisher().publish(
                            new InventoryItemClickedEvent(item.getId(), item.getName(), "room")
                    );
                    // Prompt the user with available actions:
                    int choice = JOptionPane.showOptionDialog(
                            InventoryPanel.this,
                            "What do you want to do with \"" + item.getName() + "\"?",
                            "Item Action",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Pick Up", "Inspect", "Cancel"},
                            "Pick Up"
                    );
                    if (choice == 0) { // Pick Up action
                        context.getEventPublisher().publish(
                                new InventoryItemActionEvent(item.getId(), item.getName(), "room", "pickup")
                        );
                    } else if (choice == 1) { // Inspect action
                        context.getEventPublisher().publish(
                                new InventoryItemActionEvent(item.getId(), item.getName(), "room", "inspect")
                        );
                    }
                    // "Cancel" does nothing.
                }
            }
        });


        // Add mouse listeners for player inventory.
        playerInventoryList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = playerInventoryList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Item item = playerInventoryModel.getElementAt(index);
                    context.getEventPublisher().publish(
                            new InventoryItemHoveredEvent(item.getId(), item.getName(), item.getDescription(), "player")
                    );
                }
            }
        });

        playerInventoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = playerInventoryList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Item item = playerInventoryModel.getElementAt(index);
                    context.getEventPublisher().publish(
                            new InventoryItemClickedEvent(item.getId(), item.getName(), "player")
                    );
                    int choice = JOptionPane.showOptionDialog(
                            InventoryPanel.this,
                            "What do you want to do with \"" + item.getName() + "\"?",
                            "Item Action (Player)",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Use", "Inspect", "Cancel"},
                            "Use"
                    );
                    if (choice == 0) { // Use action.
                        context.getEventPublisher().publish(
                                new InventoryItemActionEvent(item.getId(), item.getName(), "player", "use")
                        );
                    } else if (choice == 1) { // Inspect action.
                        context.getEventPublisher().publish(
                                new InventoryItemActionEvent(item.getId(), item.getName(), "player", "inspect")
                        );
                    }
                }
            }
        });

        // Create a message area:
        messageArea = new JTextArea(5, 40);
        messageArea.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(BorderFactory.createTitledBorder("Messages"));

        // Add the split pane (inventory lists) to CENTER and message area to SOUTH.
        add(splitPane, BorderLayout.CENTER);
        add(messageScroll, BorderLayout.SOUTH);

    }

    // Optionally provide a method to append messages:
    public void appendMessage(String message) {
        messageArea.append(message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    /**
     * Refreshes the inventory lists by querying the current room and player inventories.
     */
    public void refresh() {
        // Clear the current models.
        roomInventoryModel.clear();
        playerInventoryModel.clear();

        // Update room inventory.
        Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
        Inventory roomInv = currentRoom.getInventory();
        List<Item> roomItems = roomInv.getItems();
        for (Item item : roomItems) {
            roomInventoryModel.addElement(item);
        }

        // Update player inventory.
        Inventory playerInv = context.getPlayer().getInventory();
        List<Item> playerItems = playerInv.getItems();
        for (Item item : playerItems) {
            playerInventoryModel.addElement(item);
        }
    }
}


