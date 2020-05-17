package Datos;


public class Blockchain {
    int index;
    String timestamp;
    int nonce;
    String previoushash;
    String hash;
    static Blockchain previo;
    static Blockchain siguiente;
   

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getPrevioushash() {
        return previoushash;
    }

    public void setPrevioushash(String previoushash) {
        this.previoushash = previoushash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
}
