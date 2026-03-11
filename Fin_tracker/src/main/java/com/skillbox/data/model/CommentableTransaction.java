package com.skillbox.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentableTransaction extends Transaction implements Commentable {

    private List<String> comments = new ArrayList<>();

    @Override
    public double getFinalAmount() {
        return getAmount();
    }
}
