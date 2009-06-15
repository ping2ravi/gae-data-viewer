package com.app.music.server.paging;

/**
*
* @author Ravi
*/
public class PageInfo {

   private int pageNum;
   private int pageSize;
   private boolean getTotalSize;
   private boolean getAllRecords;

   /**
    * @return the pageNum
    */
   public int getPageNum() {
       return pageNum;
   }

   /**
    * @param pageNum the pageNum to set
    */
   public void setPageNum(int pageNum) {
       this.pageNum = pageNum;
   }

   /**
    * @return the pageSize
    */
   public int getPageSize() {
       return pageSize;
   }

   /**
    * @param pageSize the pageSize to set
    */
   public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
   }

   /**
    * @return the getTotalSize
    */
   public boolean isGetTotalSize() {
       return getTotalSize;
   }

   /**
    * @param getTotalSize the getTotalSize to set
    */
   public void setGetTotalSize(boolean getTotalSize) {
       this.getTotalSize = getTotalSize;
   }

   /**
    * @return the getAllRecords
    */
   public boolean isGetAllRecords() {
       return getAllRecords;
   }

   /**
    * @param getAllRecords the getAllRecords to set
    */
   public void setGetAllRecords(boolean getAllRecords) {
       this.getAllRecords = getAllRecords;
   }

}
