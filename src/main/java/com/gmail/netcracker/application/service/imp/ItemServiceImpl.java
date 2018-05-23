package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.dao.interfaces.PriorityDao;
import com.gmail.netcracker.application.dto.dao.interfaces.TagDao;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.dto.model.Tag;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ItemServiceImpl implements ItemService {

   private ItemDao itemDao;
   private PriorityDao priorityDao;
   private UserService userService;
   @Autowired
   private TagDao tagDao;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, PriorityDao priorityDao, UserService userService){
        this.itemDao = itemDao;
        this.priorityDao = priorityDao;
        this.userService = userService;
    }

    @Override
    public void update(Item item) {
        setPersonId(item);
        itemDao.update(item);
    }

    @Override
    public void delete(Long itemId) {
        itemDao.delete(itemId);
    }

    @Override
    public void add(Item item) {
        setPersonId(item);
        itemDao.setRoot(itemDao.add(item));
    }

    @Override
    public Item getItem(Long itemId) {
        return itemDao.getItem(itemId);
    }

    @Override
    public List<Item> getWishList(Long personId) {
        List<Item> wishList= itemDao.findItemsByUserId(personId);
        for (Item item: wishList){
            //item.setTags(tagDao.getTagsOfItem(item.getItemId()));
            item.setLikes(itemDao.getLikesCount(Math.toIntExact(item.getItemId())));
            System.out.println(item + "=item");
//            if(itemDao.isLiked(item.getItemId(), personId)=1) {
//                item.setLiked(true);
//            }else{
//                item.setLiked(true);
//            }
        }
        return wishList;
    }

    @Override
    public void setPersonId(Item item) {
        item.setPersonId(userService.getAuthenticatedUser().getId());
    }

    @Override
    public List<Priority> getAllPriorities() {
        return priorityDao.getAllPriority();
    }

    @Override
    public void copyItem(Long itemId) {
        Long copiedId = itemDao.insertCopiedItem(itemDao.getItem(itemId), userService.getAuthenticatedUser().getId());
        addTagsToCopiedItem(tagDao.getTagsOfItem(itemId), copiedId);
    }

    @Override
    public void bookItem(Long itemId) {
        if(itemDao.getBookerId(itemId) == 0) itemDao.setBooker(itemId, userService.getAuthenticatedUser().getId());
    }

    @Override
    public void cancelBookingItem(Long itemId) {
        itemDao.cancelBooking(itemId, userService.getAuthenticatedUser().getId());
    }

    @Override
    public void bookItemFromEvent(Long itemId, Long eventId) {
        if(itemDao.getBookerId(itemId) == 0) itemDao.setBookerFromEvent(itemId, userService.getAuthenticatedUser().getId(), eventId);
    }

    @Override
    public Set<String> parseTags(String tags) {
        Matcher m = Pattern.compile("#(\\w+)").matcher(tags);
        Set<String> formattedTags = new HashSet<>();
        while (m.find()) formattedTags.add( m.group(1).toLowerCase());
        return formattedTags;
    }

    @Override
    public void addTagsToItem(Set<String> tags, Long itemId) {
        Set<Tag> currentTags = tagDao.getTagsOfItem(itemId);
        Set<Tag> addTags = new HashSet<>();
        for(String tagString: tags){
            Tag tag = tagDao.findTagByName(tagString);
            if(tag == null){
                tag = new Tag();
                tag.setTagId(tagDao.addTag(tagString));
                tag.setName(tagString);
                tagDao.addTagToItem(tag.getTagId(), itemId);
            }
            else addTags.add(tag);
        }
        for(Tag tag: addTags){
            if(!currentTags.contains(tag))
                tagDao.addTagToItem(tag.getTagId(), itemId);
        }
        for (Tag tag: currentTags){
            if(!addTags.contains(tag))
                tagDao.deleteTagOfItem(tag.getTagId(), itemId);
        }
    }

    @Override
    public void addTagsToCopiedItem(Set<Tag> tags, Long itemId){
        for(Tag tag: tags){
            tagDao.addTagToItem(tag.getTagId(), itemId);
        }
    }

    @Override
    public void addTagsToNewItem(Set<String> tags, Long itemId){
        for(String tagString: tags){
            Tag tag = tagDao.findTagByName(tagString);
            if(tag == null){
                tag = new Tag();
                tag.setTagId(tagDao.addTag(tagString));
                tag.setName(tagString);
            }
            tagDao.addTagToItem(tag.getTagId(), itemId);
        }
    }

    @Override
    public List<Item> popularItems() {
        return itemDao.getPopularItems(5);
    }

    @Override
    public Set<Tag> getTagsOfItem(Long itemId) {
        return tagDao.getTagsOfItem(itemId);
    }

    @Override
    public void like(Long itemId, Long userId) {
        itemDao.like(itemId, userId);
    }

    @Override
    public int countLikes(int itemId) {
        return itemDao.getLikesCount(itemId);
    }

    @Override
    public boolean isLiked(Long itemId, Long userId) {
        return itemDao.isLiked(itemId, userId) != null;
    }

    @Override
    public void dislike(Long itemId, Long userId) {
        itemDao.dislike(itemId, userId);
    }
}
