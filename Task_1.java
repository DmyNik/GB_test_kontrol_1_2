import java.util.Random;
import java.util.PriorityQueue;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class ToyShop {
  
    // Пустая игрушка {
    private String idEmptyToy, nameEmptyToy;
    // }
    
    // Текущая игрушка {
    private String idToy, nameToy;
    private Integer probabilityToy;
    // }
  
    // Конструктор
    public ToyShop(String id, String name, String probability) {
      
      InitEmptyToy("", "");
      
      idToy = id;
      nameToy = name;
      probabilityToy = Integer.parseInt(probability);
  
      System.out.println("> Игрушка с идентификатором " + id + " создана.");
  
    }
    
    // Инициализация пустой игрушки
    public void InitEmptyToy(String id, String name) {
      
      if (id == "") { id = "0"; }
      if (name == "") { name = "Игрушка не выпала"; }
  
      idEmptyToy = id;
      nameEmptyToy = name;
      
    }

    // Инициализация другой игрушки
    public void InitToy(String id, String name, String probability) {
      
      InitEmptyToy("", "");
      
      idToy = id;
      nameToy = name;
      probabilityToy = Integer.parseInt(probability);
  
      System.out.println("> Текущая игрушка изменена на игрушку с идентификатором " + id + ".");
      
    }
  
    // Вывод реквизитов записанной игрушки
    public void PrintToy() {
      
      String resuString = "";
      
      resuString += "ID: " + idToy;
      resuString += "\nName: " + nameToy;
      resuString += "\nProbability: " + probabilityToy.toString();
      System.out.println(resuString);
  
    }
  
    // Заполнение массива ArrayList
    public void LaunchLotteryToy(ArrayList<HashMap<String,String>> listToy, Integer volume) {
      
      Map<String,String> price = new HashMap<String,String>();
      Map<String,String> empty = new HashMap<String,String>();
      
      // Создаем экземпляр класса Рандомайзер
      Random rand = new Random();
  
      java.time.LocalDateTime dateTime = java.time.LocalDateTime.now();
      String dateTimeString = dateTime.toString();

      price.put("id", idToy);
      price.put("name", nameToy);
      price.put("date", dateTimeString);
  
      empty.put("id", idEmptyToy);
      empty.put("name", nameEmptyToy);
      empty.put("date", dateTimeString);

      Integer rand_int = 0;
  
      for (int i = 0; i < volume; i++) {
        // Генерируем случайные целые числа в диапазоне от 0 до probabilityToy - 1
        rand_int = rand.nextInt(probabilityToy);
        rand_int++;
        if (rand_int == 1) {
          listToy.add((HashMap<String, String>) price);
        } else {
          listToy.add((HashMap<String, String>) empty);
        }
  
      }
      
    }
  
  }
  
  class ListLotteryComparator implements Comparator<ArrayList<HashMap<String,String>>> { 
    // Метод сравнения для сравнения двух строк 
    public int compare(ArrayList<HashMap<String,String>> cmp1, ArrayList<HashMap<String,String>> cmp2) { 
      // System.out.println(cmp1.get(0).get("date"));
      // System.out.println(cmp2.get(0).get("date"));
      return cmp1.get(0).get("date").compareTo(cmp2.get(0).get("date"));         
      // return 1;
    } 
  }

  public class Task_1 {
    
    private static ArrayList<HashMap<String,String>> listToy; 
    private static ToyShop myToyShop;
    private static PriorityQueue<ArrayList<HashMap<String,String>>> listLottery = new PriorityQueue<>(new ListLotteryComparator());
    
    public static void main(String[] args) {
    
      String strArgs[] = new String[10];

      String id1;
      String id2;
      String id3;
      String name1;
      String name2;
      String name3;
      String probability1;
      String probability2;
      String probability3;

      id1 = "1";
      id2 = "2";
      id3 = "3";  
      name1 = "Слон";
      name2 = "Лев";
      name3 = "Носорог";
      probability1 = "10";
      probability2 = "5";
      probability3 = "2";            
      
      strArgs[0] = id1;
      strArgs[1] = name1;
      strArgs[2] = probability1;
      strArgs[3] = id2;
      strArgs[4] = name2;
      strArgs[5] = probability2;
      strArgs[6] = id3;
      strArgs[7] = name3;
      strArgs[8] = probability3;
      
      // Демонстрация работы прогарммы
      // Создаем экземпляр класса
      myToyShop = new ToyShop(id1, name1, probability1); 
      // Запускаем розыгрыш игрушки
      myToyShop.LaunchLotteryToy(listToy = new ArrayList<HashMap<String,String>>(), 10);      
      System.out.println(" ---------------------- ");
      System.out.println("> ДЕМОНСТРАЦИЯ ОДНОГО РОЗЫГРЫША: ");
      System.out.println(" ---------------------- ");
      // Выводим на экран розыгрыш игрушки
      System.out.println(PrintLotery(listToy));
       
      System.out.println("> ДЕМОНСТРАЦИЯ СЕРИИ РОЗЫГРЫШЕЙ: ");
      System.out.println(" ---------------------- ");
      // Создаем массив из трех розыгрышей
      Integer cnt = 0; 
      for (int i = 0; i < 3; i++) {
        // Изменяем параметры игрушки
        // System.out.println(strArgs[cnt]);
        myToyShop.InitToy(strArgs[cnt++], strArgs[cnt++], strArgs[cnt++]);
        // Запускаем розыгрыш игрушки
        myToyShop.LaunchLotteryToy(listToy = new ArrayList<HashMap<String,String>>(), 10); 
        System.out.println("Розыгрыш состоялся");
        // Добавляем розыгрыш игрушки в массив
        listLottery.add(listToy);
        System.out.println("Розыгрыш добавлен в массив");
      }

      // Выводим на экран все розыгрыши
      PrintAllLotery(listLottery);

      // Записывает все розыгрыши в файл
      WriteAllLotery(listLottery);
            
    }

    public static String PrintLotery(ArrayList<HashMap<String,String>> myListToy) {
      String tmpStr = "";
      Integer cnt = 0;
      
      tmpStr += "> Результат розыгрыша игрушек: ";
      
      tmpStr += "\n ---------------------- ";
      for (HashMap<String, String> element: myListToy) {
      
        cnt++;
        tmpStr += "\nРезультат " + cnt.toString() + " ==>   \tID: " + element.get("id") + " Название: " + element.get("name") + ".";
      
      } 
      
      tmpStr += "\n ---------------------- "; 

      return tmpStr;

    }

    public static void PrintAllLotery(PriorityQueue<ArrayList<HashMap<String,String>>> myListLottery) {

      Integer cnt = 1;
      String tmpString = "";
      tmpString += " ---------------------- ";
      System.out.println(tmpString);
      for (ArrayList<HashMap<String,String>> element: myListLottery) {        
        tmpString = "";
        tmpString += "> РОЗЫГРЫШ №: " + cnt.toString();        
        tmpString += "\n> Дата проведения: " + element.get(0).get("date");        
        tmpString += "\n ---------------------- ";
        System.out.println(tmpString);
        System.out.println(PrintLotery(element));
        cnt++;
      }  

    }

    public static void WriteAllLotery(PriorityQueue<ArrayList<HashMap<String,String>>> myListLottery) {

      try {
        FileWriter writer = new FileWriter("output.txt", true);
        
        Integer cnt = 1;
        String tmpString = "";
        tmpString += "> РЕЗУЛЬТАТЫ РОЗЫГРЫШЕЙ ИГРУШЕК";
        tmpString += "\n ---------------------- ";
        writer.write(tmpString);
        for (ArrayList<HashMap<String,String>> element: myListLottery) {        
          tmpString = "";
          tmpString += "\n> РОЗЫГРЫШ №: " + cnt.toString();        
          tmpString += "\n> Дата проведения: " + element.get(0).get("date");        
          tmpString += "\n ---------------------- \n";
          
          writer.write(tmpString);
          writer.write(PrintLotery(element));

          cnt++;
        }

        writer.write("\n");
        writer.close();

        System.out.println("> Файл записан!");
  
      } catch (IOException e) {
        System.out.println("Возникла ошибка во время записи файла, проверьте данные.");
      }

    }    

  }
