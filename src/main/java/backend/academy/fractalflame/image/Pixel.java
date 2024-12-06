package backend.academy.fractalflame.image;

public record Pixel(int red, int green, int blue, int hitCount) {

    public Pixel setColor(int newR, int newG, int newB) {
        return new Pixel(newR, newG, newB, hitCount);
    }

    public Pixel hit() {
        return new Pixel(red, green, blue, hitCount + 1); // Увеличиваем hitCount
    }
}
