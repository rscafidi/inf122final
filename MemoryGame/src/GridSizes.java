public class GridSizes {

    // Fields
    private int[] allRowSizes = {2,4,6,8};
    private int[] allColSizes = {2,4,6,8};


    // Constructors
    public GridSizes() {}


    // Methods
    /**
     * Returns the allRowSizes field defined in this class
     * @return allRowSizes array
     */
    public int[] getAllRowSizes() {
        return this.allRowSizes;
    }

    /**
     * returns the allColSizes field defined in this class
     * @return allColSizes array
     */
    public int[] getAllColSizes() {
        return this.allColSizes;
    }

    /**
     * Takes the index of the row size desired and returns the row size at that index.
     * @param index index of the row size desired
     * @return the actual value at the index set as parameter
     */
    public int selectRowSize(int index) {
        return allRowSizes[index];
    }

    /**
     * Takes the index of the col size desired and returns the col size at that index.
     * @param index index of the col size desired
     * @return the actual value at the index set as parameter
     */
    public int selectColSize(int index) {
        return allColSizes[index];
    }


}
