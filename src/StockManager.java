
public class StockManager {
    public Tree_2_3 stockIDTree;
    public Tree_2_3 stockPriceTree;
    // add code here

    // 1. Initialize the system
    public void initStocks() {
        this.stockIDTree = new Tree_2_3();
        this.stockPriceTree = new Tree_2_3();
        // add code here
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        if(price <= 0f || price == null || timestamp <= 0L)
            throw new IllegalArgumentException();
        TreeNode checkIfExsist = stockIDTree.Tree_2_3_Search(stockIDTree.root, stockId, null);
        if(checkIfExsist != null)
            throw new IllegalArgumentException();
        Tree_2_3 stockTimeStampTree = new Tree_2_3<Long, Object>();
        TreeNode stockNode = new TreeNode<Long, Float, Object>(null, null, null, stockTimeStampTree.root, timestamp, price, null, 1);
        stockTimeStampTree.Tree_2_3_Insert(stockTimeStampTree.root, stockNode);

        TreeNode stockIDNode = new TreeNode<String, Float, Tree_2_3>(null, null, null,null , stockId, price, stockTimeStampTree, 1);
        stockIDTree.Tree_2_3_Insert(stockIDTree.root, stockIDNode);

        TreeNode stockPriceNode = new TreeNode<Float, String, Object>(null, null, null, null, price, stockId, null, 1);
        stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, stockPriceNode);
        // add code here
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        TreeNode nodeToRemoveStockID = stockIDTree.Tree_2_3_Search(stockIDTree.root, stockId, null);
        if(nodeToRemoveStockID == null) {
            throw new IllegalArgumentException();
        }
        Float priceToRemove = (float) nodeToRemoveStockID.secondKey;
        TreeNode nodeToRemovePrice = stockPriceTree.Tree_2_3_Search(stockPriceTree.root, priceToRemove, stockId);
        if(nodeToRemovePrice == null) {
            throw new IllegalArgumentException();
        }

        stockIDTree.Tree_2_3_Delete(stockIDTree.root, nodeToRemoveStockID);
        stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, nodeToRemovePrice);

        // add code here
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        TreeNode nodeToUpdateStockId = stockIDTree.Tree_2_3_Search(stockIDTree.root, stockId, null);
        if(nodeToUpdateStockId == null || priceDifference == 0f || timestamp <= 0L) {
            throw new IllegalArgumentException();
        }
        Float priceToUpdate = (float) nodeToUpdateStockId.secondKey;
        nodeToUpdateStockId.secondKey =(float) nodeToUpdateStockId.secondKey + priceDifference;

        TreeNode nodeToUpdatePrice = stockPriceTree.Tree_2_3_Search(stockPriceTree.root, priceToUpdate, stockId);
        nodeToUpdatePrice.Key = (float) nodeToUpdatePrice.Key + priceDifference;

        stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, nodeToUpdatePrice);
        stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, nodeToUpdatePrice);

        Tree_2_3 stockTimeStampTree = (Tree_2_3) nodeToUpdateStockId.object;
        TreeNode newTimeStampNode = new TreeNode<Long, Float, Object>(null, null, null, null, timestamp, priceDifference, null, 1);
        stockTimeStampTree.Tree_2_3_Insert(stockTimeStampTree.root, newTimeStampNode);

        // add code here
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        TreeNode stockIDNode = stockIDTree.Tree_2_3_Search(stockIDTree.root, stockId, null);
        if(stockIDNode == null)
            throw new IllegalArgumentException();
        float priceToReturn = (float)stockIDNode.secondKey;
        return priceToReturn;
        // add code here
    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        TreeNode nodeToUpdateStockId = stockIDTree.Tree_2_3_Search(stockIDTree.root, stockId, null);
        if(nodeToUpdateStockId == null || (nodeToUpdateStockId.object.Tree_2_3_Minimum(nodeToUpdateStockId.object.root)).compareKey(timestamp, null)==0)
            throw new IllegalArgumentException();
        Tree_2_3 stockTimeStampTree = (Tree_2_3) nodeToUpdateStockId.object;
        if(stockTimeStampTree.root.size == 1)
            throw new IllegalArgumentException();

        TreeNode nodeTimeStampToRemove = stockTimeStampTree.Tree_2_3_Search(stockTimeStampTree.root, timestamp, null);
        if(nodeTimeStampToRemove == null)
            throw new IllegalArgumentException();
        Float priceToRemove = (float) nodeTimeStampToRemove.secondKey;
        stockTimeStampTree.Tree_2_3_Delete(stockTimeStampTree.root, nodeTimeStampToRemove);

        TreeNode nodeToUpdatePrice = stockPriceTree.Tree_2_3_Search(stockPriceTree.root, (float)nodeToUpdateStockId.secondKey, stockId);
        nodeToUpdateStockId.secondKey = (float) nodeToUpdateStockId.secondKey - priceToRemove;

        nodeToUpdatePrice.Key = (float) nodeToUpdatePrice.Key - priceToRemove;
        stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, nodeToUpdatePrice);
        stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, nodeToUpdatePrice);

        // add code here
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 > price2)
            throw new IllegalArgumentException();
        TreeNode MinNode = stockPriceTree.Tree_2_3_Search(stockPriceTree.root, price1, null);
        TreeNode MaxNode = stockPriceTree.Tree_2_3_Search(stockPriceTree.root, price2, null);
        if (MinNode != null && MinNode == MaxNode) {
            TreeNode tempMaxNode = new TreeNode<Float, String, Object>(null, null, null, null, price2+ 0.01f, null, null, 1);
            stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, tempMaxNode);
            MaxNode  = stockPriceTree.Tree_2_3_Predecessor(tempMaxNode);
            stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, tempMaxNode);
        }
        if (MinNode == null) {
            TreeNode tempMinNode = new TreeNode<Float, String, Object>(null, null, null, null, price1, null, null, 1);
            stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, tempMinNode);
            MinNode = stockPriceTree.Tree_2_3_Successor(tempMinNode);
            stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, tempMinNode);

        }
        if (MaxNode == null) {
            TreeNode tempMaxNode = new TreeNode<Float, String, Object>(null, null, null, null, price2, null, null, 1);
            stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, tempMaxNode);
            MaxNode  = stockPriceTree.Tree_2_3_Predecessor(tempMaxNode);
            stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, tempMaxNode);

        }
        return stockPriceTree.Rank(MaxNode) - stockPriceTree.Rank(MinNode) + 1;
        // add code here
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 > price2)
            throw new IllegalArgumentException();
        int range = getAmountStocksInPriceRange(price1, price2);
        String[] stocks = new String[range];
        TreeNode MinNode = stockPriceTree.Tree_2_3_Search(stockPriceTree.root, price1, null);

        if (MinNode == null) {
            TreeNode tempMinNode = new TreeNode<Float, String, Object>(null, null, null, null, price1, null, null, 1);
            stockPriceTree.Tree_2_3_Insert(stockPriceTree.root, tempMinNode);
            MinNode = stockPriceTree.Tree_2_3_Successor(tempMinNode);
            stockPriceTree.Tree_2_3_Delete(stockPriceTree.root, tempMinNode);

        }

        for (int i = 0; i < range; i++) {
            stocks[i] = (String) MinNode.secondKey;
            MinNode = MinNode.getNext();
        }
        return stocks;

    }

}


