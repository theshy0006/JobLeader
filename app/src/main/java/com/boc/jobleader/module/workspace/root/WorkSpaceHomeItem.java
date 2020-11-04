package com.boc.jobleader.module.workspace.root;

public class WorkSpaceHomeItem {
    private String itemName;
    private int itemSrouce;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WorkSpaceHomeItem(String itemName, int itemSrouce, String content) {
        this.itemName = itemName;
        this.itemSrouce = itemSrouce;
        this.content = content;
    }

    public WorkSpaceHomeItem(String itemName, int itemSrouce) {
        this.itemName = itemName;
        this.itemSrouce = itemSrouce;
        this.content = "";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemSrouce() {
        return itemSrouce;
    }

    public void setItemSrouce(int itemSrouce) {
        this.itemSrouce = itemSrouce;
    }
}
