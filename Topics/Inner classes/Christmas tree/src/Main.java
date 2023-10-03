class ChristmasTree {

    private String color;

    public ChristmasTree(String color) {
        this.color = color;
    }

    // create method putTreeTopper()
    void putTreeTopper(String color) {
        TreeTopper treeTopper = new TreeTopper(color);
    }

    class TreeTopper {

        private String color;

        public TreeTopper(String color) {
            this.color = color;
            sparkle();
        }

        // create method sparkle()
        private void sparkle() {
            //Sparkling silver tree topper looks stunning with green Christmas tree!
            System.out.printf("Sparkling %s tree topper looks stunning with %s Christmas tree!", this.color, ChristmasTree.this.color);
        }
    }
}

// this code should work
class CreateHoliday {

    public static void main(String[] args) {

        ChristmasTree christmasTree = new ChristmasTree("green");
        christmasTree.putTreeTopper("silver");
    }
}