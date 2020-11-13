package tw.task2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class BarChartSample extends Application {
    final static String avgL = "Large batch";
    final static String avgM = "Medium batch";
    final static String avgS = "Small batch";

    @Override public void start(Stage stage) throws InterruptedException {
        int M = 1000;
        int P = 10;
        int K = 10;

//        int M = 10000;
//        int P = 100;
//        int K = 100;

//        int M = 100000;
//        int P = 1000;
//        int K = 1000;


        stage.setTitle("Lab4 Bar Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Method average time");
        xAxis.setLabel("Method");
        yAxis.setLabel("Average time");


        Thread[] prodThreads = new Thread[P];
        Thread[] consThreads = new Thread[K];


        List<Long> avgTimeS = new ArrayList<>();
        List<Long> avgTimeM = new ArrayList<>();
        List<Long> avgTimeL = new ArrayList<>();


        IBuffer buffer = new NaiveBuffer(2*M);
        for(int i=0; i<P; i++) {
            prodThreads[i] = new Thread(new Producer(buffer, M, avgTimeS, avgTimeM, avgTimeL));
            prodThreads[i].start();
        }
        for(int i=0; i<K; i++) {
            consThreads[i] = new Thread(new Consumer(buffer, M, avgTimeS, avgTimeM, avgTimeL));
            consThreads[i].start();
        }

        while(avgTimeL.size() <= 500000) {
            sleep(1000);
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Naive");
        series1.getData().add(new XYChart.Data(avgL, List.copyOf(avgTimeL).stream().mapToDouble(a->a).average().getAsDouble()));
        series1.getData().add(new XYChart.Data(avgM, List.copyOf(avgTimeM).stream().mapToDouble(a->a).average().getAsDouble()));
        series1.getData().add(new XYChart.Data(avgS, List.copyOf(avgTimeS).stream().mapToDouble(a->a).average().getAsDouble()));


        for(int i=0; i<P; i++) {
            prodThreads[i].interrupt();
        }
        for(int i=0; i<K; i++) {
            consThreads[i].interrupt();
        }
        System.out.println("Halfway done");


        List<Long> avgTimeS2 = new ArrayList<>();
        List<Long> avgTimeM2 = new ArrayList<>();
        List<Long> avgTimeL2 = new ArrayList<>();

        IBuffer buffer2 = new PortionBuffer(2*M);
        for(int i=0; i<P; i++) {
            prodThreads[i] = new Thread(new Producer(buffer2, M, avgTimeS2, avgTimeM2, avgTimeL2));
            prodThreads[i].start();
        }
        for(int i=0; i<K; i++) {
            consThreads[i] = new Thread(new Consumer(buffer2, M, avgTimeS2, avgTimeM2, avgTimeL2));
            consThreads[i].start();
        }
        while(avgTimeL2.size() <= 500000) {
            sleep(1000);
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portion");
        series2.getData().add(new XYChart.Data(avgL, List.copyOf(avgTimeL2).stream().mapToDouble(a->a).average().getAsDouble()));
        series2.getData().add(new XYChart.Data(avgM, List.copyOf(avgTimeM2).stream().mapToDouble(a->a).average().getAsDouble()));
        series2.getData().add(new XYChart.Data(avgS, List.copyOf(avgTimeS2).stream().mapToDouble(a->a).average().getAsDouble()));

        for(int i=0; i<P; i++) {
            prodThreads[i].interrupt();
        }
        for(int i=0; i<K; i++) {
            consThreads[i].interrupt();
        }

        System.out.println("L to S ratio Naive:   " + List.copyOf(avgTimeL).stream().mapToDouble(a->a).average().getAsDouble()/List.copyOf(avgTimeS).stream().mapToDouble(a->a).average().getAsDouble());
        System.out.println("L to S ratio Portion: " + List.copyOf(avgTimeL2).stream().mapToDouble(a->a).average().getAsDouble()/List.copyOf(avgTimeS2).stream().mapToDouble(a->a).average().getAsDouble());



        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}