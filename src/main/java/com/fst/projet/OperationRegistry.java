package com.fst.projet;

import java.util.HashMap;
import java.util.Map;

public class OperationRegistry {

    private static final Map<String, ImageOperation> registry = new HashMap<>();

    static {
        register(new Rotation90());
        register(new Rotation180());
        register(new Rotation270());
        register(new FlipH());
        register(new FlipV());
        register(new RGBSwapFilter());
        register(new GrayscaleFilter());
        register(new SepiaFilter());
        register(new PrewittFilter());
    }

    private static void register(ImageOperation op) {
        registry.put(op.getId(), op);
    }

    public static ImageOperation getOperation(String id) {
        return registry.get(id);
    }
}