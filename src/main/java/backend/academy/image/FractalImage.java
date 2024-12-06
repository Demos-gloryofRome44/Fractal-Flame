package backend.academy.image;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[] tempData = new Pixel[width * height];

        for (int i = 0; i < width * height; ++i) {
            tempData[i] = new Pixel(0, 0, 0, 0);
        }
        return new FractalImage(tempData, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel getPixel(int x, int y) {
        if (contains(x, y)) {
            return data[y * width + x];
        }
        return null;
    }

    public void setPixel(int x, int y, Pixel pixel) {
        data[y * width + x] = pixel;
    }
}
