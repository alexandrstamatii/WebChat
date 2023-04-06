package com.astamatii.endava.webchat.dto;

import lombok.Data;

@Data
public class RoomInfoDto {
    private String roomName;
    private String roomDescription;
    private boolean passwordProtected;
}
