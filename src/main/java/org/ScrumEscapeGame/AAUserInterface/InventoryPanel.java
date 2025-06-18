package org.ScrumEscapeGame.AAUserInterface;

import org.ScrumEscapeGame.AAEvents.InventoryItemActionEvent;
import org.ScrumEscapeGame.AAEvents.InventoryItemClickedEvent;
import org.ScrumEscapeGame.AAEvents.InventoryItemHoveredEvent;
import org.ScrumEscapeGame.AAGame.GameContext;
import org.ScrumEscapeGame.GameObjects.Inventory;
import org.ScrumEscapeGame.GameObjects.Room;
import org.ScrumEscapeGame.Items.Item;
import org.ScrumEscapeGame.Monster.MonsterManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InventoryPanel extends JPanel {
    // Fields for the two list models, lists, etc.
    private final DefaultListModel<Item> roomInventoryModel;
    private final JList<Item> roomInventoryList;
    private final DefaultListModel<Item> playerInventoryModel;
    private final JList<Item> playerInventoryList;
    private JTextArea messageArea;

    // Game context needed for updating lists and publishing events.
    private final GameContext context;
    private final boolean onlyPlayerInventory; // If true, show only player inventory.
    // Optional callback for item usage (only used in question mode).
    private ItemUsageCallback usageCallback;

    public InventoryPanel(GameContext context, GameUIService uiService, boolean onlyPlayerInventory) {
        this.context = context;
        this.onlyPlayerInventory = onlyPlayerInventory;
        setLayout(new BorderLayout());

        // In full mode, bind the "I" key to toggle the global inventory.
        if (!onlyPlayerInventory) {
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke("I"), "toggleInventory");
            this.getActionMap().put("toggleInventory", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    uiService.toggleInventoryPanel();
                }
            });
        }

        // Set up room inventory list only if not in question mode.
        roomInventoryModel = new DefaultListModel<>();
        roomInventoryList = new JList<>(roomInventoryModel);
        roomInventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane roomScroll = new JScrollPane(roomInventoryList);
        roomScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Room Inventory",
                TitledBorder.LEFT, TitledBorder.TOP));

        // Set up player inventory list.
        playerInventoryModel = new DefaultListModel<>();
        playerInventoryList = new JList<>(playerInventoryModel);
        playerInventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane playerScroll = new JScrollPane(playerInventoryList);
        playerScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Player Inventory",
                TitledBorder.LEFT, TitledBorder.TOP));

        // Layout: full mode uses a JSplitPane, question mode shows only the player inventory.
        if (!onlyPlayerInventory) {
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, roomScroll, playerScroll);
            splitPane.setDividerLocation(300);
            add(splitPane, BorderLayout.CENTER);
        } else {
            add(playerScroll, BorderLayout.CENTER);
        }

        // Add mouse listeners for room inventory in full mode.
        if (!onlyPlayerInventory) {
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
                        if (choice == 0) { // Pick Up
                            context.getEventPublisher().publish(
                                    new InventoryItemActionEvent(item.getId(), item.getName(), "room", "pickup")
                            );
                        } else if (choice == 1) { // Inspect
                            context.getEventPublisher().publish(
                                    new InventoryItemActionEvent(item.getId(), item.getName(), "room", "inspect")
                            );
                        }
                        MonsterManager.getInstance().tick(
                                uiService.getPlayer(),
                                context.getEventPublisher()
                        );
                        uiService.refreshStatus();

                    }
                }
            });
        }

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

        // The key part: if we are in question mode, override "Use" and "Inspect" actions
        // so they trigger the usageCallback.
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
                        // Always publish the event.
                        context.getEventPublisher().publish(
                                new InventoryItemActionEvent(item.getId(), item.getName(), "player", "use")
                        );
                    } else if (choice == 1) { // Inspect.
                        context.getEventPublisher().publish(
                                new InventoryItemActionEvent(item.getId(), item.getName(), "player", "inspect")
                        );
                    }
                    MonsterManager.getInstance().tick(
                            uiService.getPlayer(),
                            context.getEventPublisher()
                    );
                    uiService.refreshStatus();

                }
            }
        });


        // Create a message area for output.
        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(BorderFactory.createTitledBorder("Messages"));
        add(messageScroll, BorderLayout.SOUTH);
    }

    // Append messages.
    public void appendMessage(String message) {
        messageArea.append(message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    /**
     * Refreshes the inventory lists.
     */
    public void refresh() {
        if (!onlyPlayerInventory) {
            roomInventoryModel.clear();
        }
        playerInventoryModel.clear();

        if (!onlyPlayerInventory) {
            Room currentRoom = context.getRoomManager().getRoom(context.getPlayer().getPosition());
            Inventory roomInv = currentRoom.getInventory();
            List<Item> roomItems = roomInv.getItems();
            if (roomItems == null) {
                roomItems = new ArrayList<>();
            }
            for (Item item : roomItems) {
                roomInventoryModel.addElement(item);
            }
        }
        Inventory playerInv = context.getPlayer().getInventory();
        List<Item> playerItems = playerInv.getItems();
        if (playerItems == null) {
            playerItems = new ArrayList<>();
        }
        for (Item item : playerItems) {
            playerInventoryModel.addElement(item);
        }
    }

    public void clearMessages() {
        messageArea.setText("");
    }

}




