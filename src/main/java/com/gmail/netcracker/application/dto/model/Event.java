package com.gmail.netcracker.application.dto.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Event {
    private Long eventId;
    private String name;
    private String description;
    private Long creator;
    private Long duration;
    private String dateStart;
    private String dateEnd;
    private String endRepeat;
    private String periodicity;
    private Long type;
    private Boolean draft;
    private Long typeId;
    private Double width;
    private Double longitude;
    private String eventPlaceName;
    private String photo;
    private Long priorityId;

    public Boolean isDraft() {
        return draft;
    }

    public void setDraft(Boolean isDraft) {
        this.draft = isDraft;
    }
}
