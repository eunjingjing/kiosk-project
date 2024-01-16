package com.example.java_project;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class tensor extends AppCompatActivity {
    private String selectedMenu; // 이전 화면에서 전달된 선택한 메뉴
    private Interpreter tflite;
    Button option,cancle, order;
    private String[] labels = {"ItalianBMT", "EggMayo", "Steak&Cheese", "RoastChicken", "K-Barbecue", "Mushroom", "Shrimp", "ChickenSlice", "RotisserieBarbecue", "TurkeyBacon", "Veggie", "SpicyItalian"};
    float a1,a2,a3,a4,a5,a6;
    SeekBar s1,s2,s3,s4,s5,s6;
    TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfmenu);
        // 이전 선택한 메뉴 가져오기
        selectedMenu = getIntent().getStringExtra("selectedMenu");
        if (selectedMenu == null) {
            // 예외 처리 코드: 선택한 메뉴가 없을 때
            Log.e("OrderPage2", "No selected menu provided.");
            // 추가적인 예외 처리 또는 기본 메뉴 설정 등을 수행할 수 있습니다.
        }
        option = findViewById(R.id.option);
        cancle = findViewById(R.id.cancle);
        order = findViewById(R.id.order);
        resultTextView = findViewById(R.id.resultTextView);
        s1 = findViewById(R.id.Spicy);
        s2 = findViewById(R.id.protein);
        s3 = findViewById(R.id.vegetable);
        s4 = findViewById(R.id.sweetness);
        s5 = findViewById(R.id.roasting);
        s6 = findViewById(R.id.price);
        s1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int x =s1.getProgress();
                if(x==1){
                    a1 = 0.1F;
                } else if (x==2) {
                    a1 = 0.2F;
                }else if (x==3) {
                    a1 = 0.3F;
                }else if (x==4) {
                    a1 = 0.4F;
                }else if (x==5) {
                    a1 = 0.5F;
                }else if (x==6) {
                    a1 = 0.6F;
                }else if (x==7) {
                    a1 = 0.7F;
                }else if (x==8) {
                    a1 = 0.8F;
                }else if (x==9) {
                    a1 = 0.9F;
                }else if (x==10) {
                    a1 = 1.0F;
                }
            }
        });
        s2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int x =s2.getProgress();
                if(x==1){
                    a2 = 0.1F;
                } else if (x==2) {
                    a2 = 0.2F;
                }else if (x==3) {
                    a2 = 0.3F;
                }else if (x==4) {
                    a2 = 0.4F;
                }else if (x==5) {
                    a2 = 0.5F;
                }else if (x==6) {
                    a2 = 0.6F;
                }else if (x==7) {
                    a2 = 0.7F;
                }else if (x==8) {
                    a2 = 0.8F;
                }else if (x==9) {
                    a2 = 0.9F;
                }else if (x==10) {
                    a2 = 1.0F;
                }
            }
        });
        s3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int x =s3.getProgress();
                if(x==1){
                    a3 = 0.1F;
                } else if (x==2) {
                    a3 = 0.2F;
                }else if (x==3) {
                    a3 = 0.3F;
                }else if (x==4) {
                    a3 = 0.4F;
                }else if (x==5) {
                    a3 = 0.5F;
                }else if (x==6) {
                    a3 = 0.6F;
                }else if (x==7) {
                    a3 = 0.7F;
                }else if (x==8) {
                    a3 = 0.8F;
                }else if (x==9) {
                    a3 = 0.9F;
                }else if (x==10) {
                    a3 = 1.0F;
                }
            }
        });
        s4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int x =s1.getProgress();
                if(x==1){
                    a4 = 0.1F;
                } else if (x==2) {
                    a4 = 0.2F;
                }else if (x==3) {
                    a4 = 0.3F;
                }else if (x==4) {
                    a4 = 0.4F;
                }else if (x==5) {
                    a4 = 0.5F;
                }else if (x==6) {
                    a4 = 0.6F;
                }else if (x==7) {
                    a4 = 0.7F;
                }else if (x==8) {
                    a4 = 0.8F;
                }else if (x==9) {
                    a4 = 0.9F;
                }else if (x==10) {
                    a4 = 1.0F;
                }
            }
        });
        s5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int x =s5.getProgress();
                if(x==1){
                    a5 = 0.1F;
                } else if (x==2) {
                    a5 = 0.2F;
                }else if (x==3) {
                    a5 = 0.3F;
                }else if (x==4) {
                    a5 = 0.4F;
                }else if (x==5) {
                    a5 = 0.5F;
                }else if (x==6) {
                    a5 = 0.6F;
                }else if (x==7) {
                    a5 = 0.7F;
                }else if (x==8) {
                    a5 = 0.8F;
                }else if (x==9) {
                    a5 = 0.9F;
                }else if (x==10) {
                    a5 = 1.0F;
                }
            }
        });
        s6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int x =s6.getProgress();
                if(x==1){
                    a6 = 0.1F;
                } else if (x==2) {
                    a6 = 0.2F;
                }else if (x==3) {
                    a6 = 0.3F;
                }else if (x==4) {
                    a6 = 0.4F;
                }else if (x==5) {
                    a6 = 0.5F;
                }else if (x==6) {
                    a6 = 0.6F;
                }else if (x==7) {
                    a6 = 0.7F;
                }else if (x==8) {
                    a6 = 0.8F;
                }else if (x==9) {
                    a6 = 0.9F;
                }else if (x==10) {
                    a6 = 1.0F;
                }
            }
        });
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAI(a1,a2,a3,a4,a5,a6);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMenu = resultTextView.getText().toString();
                Toast.makeText(getApplicationContext(), selectedMenu, Toast.LENGTH_SHORT).show();

                // 주문 결과 데이터를 Intent에 담아서 현재 Activity 종료
                Intent intent = new Intent(tensor.this, OrderPage.class);
                intent.putExtra("selectedMenu", selectedMenu);
                intent.putExtra("selectedBread", "플랫브래드");
                intent.putExtra("selectedSauce", "스위트 어니언");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void menuAI(float arg1,float arg2,float arg3,float arg4,float arg5,float arg6){
        try {
            // Load the TensorFlow Lite model
            tflite = new Interpreter(loadModelFile());

            // Example: Make a prediction using test data
            float[] testData = {arg1, arg2, arg3, arg4, arg5,arg6};  // Adjust the input data based on your features
            String predictedClass = classifySandwich(testData);
            Log.d("test",predictedClass);
            if(predictedClass.equals("ItalianBMT")){
                predictedClass="이탈리안 BMT";
            }else if(predictedClass.equals("EggMayo")){
                predictedClass="에그마요";
            }else if(predictedClass.equals("Steak&Cheese")){
                predictedClass="스테이크 & 치즈";
            }else if(predictedClass.equals("RoastChicken")){
                predictedClass="로스트 치킨";
            }else if(predictedClass.equals("K-Barbecue")){
                predictedClass="K-바비큐";
            }else if(predictedClass.equals("Mushroom")){
                predictedClass="머쉬룸";
            }else if(predictedClass.equals("Shrimp")){
                predictedClass="쉬림프";
            }else if(predictedClass.equals("ChickenSlice")){
                predictedClass="치킨 슬라이스";
            }else if(predictedClass.equals("RotisserieBarbecue")){
                predictedClass="로티세리 바비큐 치킨";
            }else if(predictedClass.equals("TurkeyBacon")){
                predictedClass="터키 베이컨";
            }else if(predictedClass.equals("Veggie")){
                predictedClass="베지";
            }else if(predictedClass.equals("SpicyItalian")){
                predictedClass="스파이시 이탈리안";
            }
            // Display the predicted class
            resultTextView.setText(predictedClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("sandwich_classifier_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());

        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        ByteBuffer modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        return modelBuffer;
    }

    private String classifySandwich(float[] input) {
        // Preprocess input data if needed
        // ...

        // Feed input data to the model
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(input.length * Float.BYTES);
        inputBuffer.order(java.nio.ByteOrder.nativeOrder());
        inputBuffer.rewind();
        for (float value : input) {
            inputBuffer.putFloat(value);
        }

        // Perform inference
        float[][] output = new float[1][labels.length];
        tflite.run(inputBuffer, output);

        // Postprocess the output to get the predicted class
        int predictedClassIndex = argmax(output[0]);
        return labels[predictedClassIndex];
    }

    private int argmax(float[] array) {
        int maxIndex = 0;
        float maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxIndex = i;
                maxValue = array[i];
            }
        }
        return maxIndex;
    }
}