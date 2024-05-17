package com.heeroes.setset.attachedfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachedFile {
    private int id;
    private String imageKey;
    private String originalName;
    private int articleId;
}
