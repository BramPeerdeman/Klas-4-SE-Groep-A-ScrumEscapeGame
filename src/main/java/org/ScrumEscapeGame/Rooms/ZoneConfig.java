package org.ScrumEscapeGame.Rooms;

import java.util.List;

/**
 * ZoneConfig encapsulates configuration details for a game zone.
 * It contains a zone name and a list of room definitions to be used when
 * creating the zone’s rooms.
 */
public class ZoneConfig {
    private final String zoneName;
    private final List<RoomDefinition> roomDefinitions;

    /**
     * Constructs a ZoneConfig.
     *
     * @param zoneName        the name of the zone.
     * @param roomDefinitions the list of room definitions for this zone.
     */
    public ZoneConfig(String zoneName, List<RoomDefinition> roomDefinitions) {
        this.zoneName = zoneName;
        this.roomDefinitions = roomDefinitions;
    }

    /**
     * Returns the zone name.
     *
     * @return the zone name.
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * Returns the list of room definitions.
     *
     * @return a list of RoomDefinition objects.
     */
    public List<RoomDefinition> getRoomDefinitions() {
        return roomDefinitions;
    }
}



