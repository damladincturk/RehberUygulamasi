package com.example.rehber.rehberuygulamasi;

/**
 * Created by hakan on 9.05.2017.
 */

public class resim {
    private int id;
    private byte[] image;

    public resim(int id, byte[] image) {
        this.id = id;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
