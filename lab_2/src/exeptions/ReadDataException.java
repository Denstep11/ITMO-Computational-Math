package exeptions;

public class ReadDataException extends Exception{
    public ReadDataException(){
        super("Не корректный ввод данных");
    }
}
