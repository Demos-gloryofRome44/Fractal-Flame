package backend.academy;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        return new FractalImage(new Pixel[width * height], width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        return data[y * width + x];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        data[y * width + x] = pixel;
    }
}
