package aplikasiconsole;

public class Laptop {
    private final String id;
    private final String tipe;
    private int stok;

    // Constructor
    public Laptop(String id, String tipe, int stokAwal) {
        this.id = id;
        this.tipe = tipe;
        this.stok = stokAwal;
    }

    // Method: Mengecek Ketersediaan Stok (Fitur 3)
    public int cekStok() {
        return stok;
    }

    // Method: Mengurangi Stok (Fitur 2 & 3)
    public boolean kurangiStok() {
        if (stok > 0) {
            stok--;
            return true; // Berhasil dikurangi
        }
        return false; // Gagal (stok 0)
    }

    // Method: Menambah Stok (untuk simulasi pengembalian)
    public void tambahStok() {
        stok++;
    }

    // Method: Mengembalikan Info (Fitur 1)
    public String getInfo() {
        return String.format("| %-2s | %-20s | %-4d | %-8s |", 
            id, tipe, stok, (stok > 0 ? "Tersedia" : "Dipinjam"));
    }

    // Getter dasar
    public String getId() {
        return id;
    }

    public String getTipe() {
        return tipe;
    }
}