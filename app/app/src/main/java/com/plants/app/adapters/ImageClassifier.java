package com.plants.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.plants.app.ml.Model1;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ImageClassifier {
    private static final String[] plants = {"Кактус", "Мимоза", "Монстера", "Орхидея", "Роза"};
    public static int IMAGE_SIZE = 100;

    public static String classifyImage(Bitmap bitmap, Context context) {
        bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);

        try {
            Model1 model = Model1.newInstance(context);
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 100, 100, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
            bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            int pixel = 0;

            for(int i = 0; i < IMAGE_SIZE; i++) {
                for(int j = 0; j < IMAGE_SIZE; j++) {
                    int pixelValue = intValues[pixel++]; //RGB together
                    byteBuffer.putFloat(((pixelValue >> 16) & 0xFF) * (1.f));
                    byteBuffer.putFloat(((pixelValue >> 8) & 0xFF) * (1.f));
                    byteBuffer.putFloat((pixelValue & 0xFF) * (1.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            Model1.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            model.close();
            return predictHandler(outputFeature0);

        } catch (IOException e) {
            Log.e("ImageClassifier", "Import error",e);
        }
        return null;
    }

    public static String[] getPlants() {
        return plants;
    }

    private static String predictHandler(TensorBuffer outputFeature) {
        float[] confidence = outputFeature.getFloatArray();
        int maxPos = 0;
        float maxConfidence = 0;

        for (int i = 0; i < confidence.length; i++) {
            if (confidence[i] > maxConfidence) {
                maxConfidence = confidence[i];
                maxPos = i;
            }
        }

        if (maxConfidence < 0.7) {
            return null;
        }

        double[] roundConfidence = new double[confidence.length];
        for (int i = 0; i < confidence.length; i++) {
            roundConfidence[i] = Math.round(confidence[i] * 10.0) / 10.0;
        }
        Arrays.sort(roundConfidence);
        if (roundConfidence[confidence.length - 1] == roundConfidence[confidence.length - 2]) {
            return null;
        }
        return plants[maxPos];
    }
}
