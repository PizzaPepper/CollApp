package VEM.Movil.collapp

class ImageIndex {

    private val index = ArrayList<Int>()

    constructor() {
        index.add(R.drawable.png_001_acorn)
        index.add(R.drawable.png_002_moon)
        index.add(R.drawable.png_003_wood)
        index.add(R.drawable.png_004_bee)
        index.add(R.drawable.png_005_tree)
        index.add(R.drawable.png_006_mushroom)
        index.add(R.drawable.png_007_world)
        index.add(R.drawable.png_008_leaf)
        index.add(R.drawable.png_009_leaf)
        index.add(R.drawable.png_010_tulip)
        index.add(R.drawable.png_011_sunflower)
        index.add(R.drawable.png_012_plant)
        index.add(R.drawable.png_013_fields)
        index.add(R.drawable.png_014_cactus)
        index.add(R.drawable.png_015_cloud)
        index.add(R.drawable.png_016_desert)
        index.add(R.drawable.png_017_sun)
        index.add(R.drawable.png_018_forest)
        index.add(R.drawable.png_019_ladybug)
        index.add(R.drawable.png_021_water)
        index.add(R.drawable.png_022_earth)
        index.add(R.drawable.png_023_snow)
        index.add(R.drawable.png_024_mountain)
        index.add(R.drawable.png_025_grass)
        index.add(R.drawable.png_026_rain)
        index.add(R.drawable.png_027_fish)
        index.add(R.drawable.png_028_tree)
        index.add(R.drawable.png_029_volcano)
        index.add(R.drawable.png_030_beach)
    }

    public fun getIDImage(i: Int): Int {
        return index[i]
    }


}