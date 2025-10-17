import java.util.*;

// ========== ADAPTER PATTERN ==========
// Allows multiple media sources (Local, HLS, Remote API)
interface MediaSource {
    void load(String fileName);
}

class LocalFilePlayer implements MediaSource {
    public void load(String fileName) {
        System.out.println("Loading local file: " + fileName);
    }
}

class HLSStreamPlayer implements MediaSource {
    public void load(String fileName) {
        System.out.println("Connecting to HLS stream: " + fileName);
    }
}

class RemoteAPIPlayer implements MediaSource {
    public void load(String fileName) {
        System.out.println("Fetching remote media via API: " + fileName);
    }
}

// ========== BRIDGE PATTERN ==========
// Allows switching between hardware/software rendering
interface Renderer {
    void render(String fileName);
}

class HardwareRenderer implements Renderer {
    public void render(String fileName) {
        System.out.println("Rendering " + fileName + " with hardware acceleration.");
    }
}

class SoftwareRenderer implements Renderer {
    public void render(String fileName) {
        System.out.println("Rendering " + fileName + " using software mode.");
    }
}

// ========== DECORATOR PATTERN ==========
// Allows dynamic add-ons: subtitles, equalizer, watermark
interface Player {
    void play();
}

class BaseMediaPlayer implements Player {
    private String fileName;
    private Renderer renderer;

    public BaseMediaPlayer(String fileName, Renderer renderer) {
        this.fileName = fileName;
        this.renderer = renderer;
    }

    public void play() {
        renderer.render(fileName);
        System.out.println("Playing " + fileName + "...");
    }
}

abstract class PlayerDecorator implements Player {
    protected Player decoratedPlayer;
    public PlayerDecorator(Player player) {
        this.decoratedPlayer = player;
    }
    public void play() {
        decoratedPlayer.play();
    }
}

class SubtitleDecorator extends PlayerDecorator {
    public SubtitleDecorator(Player player) {
        super(player);
    }
    public void play() {
        super.play();
        System.out.println("Subtitles enabled.");
    }
}

class EqualizerDecorator extends PlayerDecorator {
    public EqualizerDecorator(Player player) {
        super(player);
    }
    public void play() {
        super.play();
        System.out.println("Equalizer effect applied.");
    }
}

class WatermarkDecorator extends PlayerDecorator {
    public WatermarkDecorator(Player player) {
        super(player);
    }
    public void play() {
        super.play();
        System.out.println("Watermark applied.");
    }
}

// ========== COMPOSITE PATTERN ==========
// Supports nested playlists (playlist inside playlist)
interface PlaylistComponent {
    void showDetails();
}

class Song implements PlaylistComponent {
    private String name;
    public Song(String name) {
        this.name = name;
    }
    public void showDetails() {
        System.out.println("Song: " + name);
    }
}

class Playlist implements PlaylistComponent {
    private String name;
    private List<PlaylistComponent> components = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void add(PlaylistComponent component) {
        components.add(component);
    }

    public void showDetails() {
        System.out.println("Playlist: " + name);
        for (PlaylistComponent c : components) {
            c.showDetails();
        }
    }
}

// ========== PROXY PATTERN ==========
// Adds caching to remote streaming
interface RemoteMedia {
    void playStream(String fileName);
}

class RealRemoteMedia implements RemoteMedia {
    public void playStream(String fileName) {
        System.out.println("Streaming remote media: " + fileName);
    }
}

class RemoteMediaProxy implements RemoteMedia {
    private RealRemoteMedia realRemoteMedia;
    private String cache;

    public void playStream(String fileName) {
        if (cache == null || !cache.equals(fileName)) {
            System.out.println("Caching remote stream for: " + fileName);
            realRemoteMedia = new RealRemoteMedia();
            cache = fileName;
        } else {
            System.out.println("Using cached version for: " + fileName);
        }
        realRemoteMedia.playStream(fileName);
    }
}

// ========== MAIN APPLICATION ==========
public class ImprovedMediaPlayer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter media source type (local/hls/remote): ");
        String sourceType = sc.nextLine();
        System.out.println("Enter file name: ");
        String fileName = sc.nextLine();

        MediaSource source;
        switch (sourceType.toLowerCase()) {
            case "hls":
                source = new HLSStreamPlayer();
                break;
            case "remote":
                source = new RemoteAPIPlayer();
                break;
            default:
                source = new LocalFilePlayer();
                break;
        }
        source.load(fileName);

        // Renderer setup
        System.out.println("Use hardware rendering? (yes/no): ");
        Renderer renderer = sc.nextLine().equalsIgnoreCase("yes")
                ? new HardwareRenderer()
                : new SoftwareRenderer();

        Player player = new BaseMediaPlayer(fileName, renderer);

        // Add features dynamically
        System.out.println("Enable subtitles? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes"))
            player = new SubtitleDecorator(player);

        System.out.println("Enable equalizer? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes"))
            player = new EqualizerDecorator(player);

        System.out.println("Enable watermark? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes"))
            player = new WatermarkDecorator(player);

        // Simulate remote caching (Proxy)
        if (sourceType.equalsIgnoreCase("remote")) {
            RemoteMediaProxy proxy = new RemoteMediaProxy();
            proxy.playStream(fileName);
        }

        // Playlist example (Composite)
        Playlist playlist = new Playlist("My Favorites");
        playlist.add(new Song(fileName));
        playlist.add(new Song("Next Song.mp3"));

        Playlist mix = new Playlist("Mixed Hits");
        mix.add(new Song("Hit1.mp3"));
        mix.add(new Song("Hit2.mp3"
));
        playlist.add(mix);

        System.out.println("\n--- Playlist Details ---");
        playlist.showDetails();

        System.out.println("\n--- Now Playing ---");
        player.play();

        sc.close();
    }
}