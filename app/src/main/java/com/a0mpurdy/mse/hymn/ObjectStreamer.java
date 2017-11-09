package com.a0mpurdy.mse.hymn;

import java.io.IOException;
import java.io.ObjectInputStream;

public interface ObjectStreamer {
    ObjectInputStream open(String filename) throws IOException;
}
