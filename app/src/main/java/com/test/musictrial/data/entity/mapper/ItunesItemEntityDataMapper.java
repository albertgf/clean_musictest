package com.test.musictrial.data.entity.mapper;

import com.test.musictrial.data.entity.ApiItunesItem;
import com.test.musictrial.domain.ItunesItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by albert on 17/02/15.
 */
public class ItunesItemEntityDataMapper {

    public ItunesItemEntityDataMapper() {
    }

    public ItunesItem transform(ApiItunesItem apiItunesItem) {
        ItunesItem item = null;
        if(apiItunesItem != null) {
            item = new ItunesItem();
            item.setTrackId(item.getTrackId());
            item.setArtist(apiItunesItem.getArtistName());
            item.setCollection(apiItunesItem.getCollectionName());
            item.setTitle(apiItunesItem.getTrackName());
            item.setDate(apiItunesItem.getReleaseDate());
            item.setCurrency(apiItunesItem.getCurrency());
            item.setPrice(apiItunesItem.getTrackPrice());
            item.setGenere(apiItunesItem.getPrimaryGenreName());
            item.setThumbnail(apiItunesItem.getArtworkUrl100());
            item.setDuration(apiItunesItem.getTrackTimeMillis());
            item.setStreamUrl(apiItunesItem.getPreviewUrl());
        }

        return item;
    }

    public Collection<ItunesItem> transform(Collection<ApiItunesItem> apiItunesCollection) {
        List<ItunesItem> itunesList = new ArrayList<ItunesItem>();

        ItunesItem item;
        for (ApiItunesItem apiItem : apiItunesCollection) {
            item = transform(apiItem);
            if (item != null) {
                itunesList.add(item);
            }
        }

        return itunesList;
    }
}
