public class GridSizes {

    private int[] allRowSizes = {2,4,6,8};
    private int[] allColSizes = {2,4,6,8};

    public GridSizes() {}

    public int[] getAllRowSizes() {
        return this.allRowSizes;
    }

    public int[] getAllColSizes() {
        return this.allColSizes;
    }

    public int selectRowSize(int index) {
        return allRowSizes[index];
    }

    public int selectColSize(int index) {
        return allColSizes[index];
    }


}
