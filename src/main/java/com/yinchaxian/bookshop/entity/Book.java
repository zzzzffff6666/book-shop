package com.yinchaxian.bookshop.entity;

public class Book {
    private long bookId;
    private int cateId;
    private String cname;
    private int storeId;
    private String name;
    private String imageUrl;
    private String outline;
    private String author;
    private String press;
    private String version;
    private String publishDate;
    private String isbn;
    private int pages;
    private String catalog;
    private String packStyle;
    private int storeMount;
    private float price;
    private float marketPrice;
    private float memberPrice;
    private float discount;
    private int dealMount;
    private int lookMount;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPackStyle() {
        return packStyle;
    }

    public void setPackStyle(String packStyle) {
        this.packStyle = packStyle;
    }

    public int getStoreMount() {
        return storeMount;
    }

    public void setStoreMount(int storeMount) {
        this.storeMount = storeMount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(float memberPrice) {
        this.memberPrice = memberPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getDealMount() {
        return dealMount;
    }

    public void setDealMount(int dealMount) {
        this.dealMount = dealMount;
    }

    public int getLookMount() {
        return lookMount;
    }

    public void setLookMount(int lookMount) {
        this.lookMount = lookMount;
    }
}
