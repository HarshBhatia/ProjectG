package com.example.harsh.projectg.models;

import com.example.harsh.projectg.models.ImagesMetadata;
import com.example.harsh.projectg.models.Pagination;
import com.example.harsh.projectg.models.Status;

import java.util.List;

/**
 * Created by harsh on 10/12/16.
 */

public class GiphyResponse {
    public List<ImagesMetadata> data;
    public Status meta;
    public Pagination pagination;
}
