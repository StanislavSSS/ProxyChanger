package ru.kss;

/**
 * Hello world!
 *
 */
public class App{
    public static void main(String[] args){
    }

    static boolean QUERY = true;
    static boolean CMD = false;
    public static Wininet wininet = Wininet.INSTANCE;

    public boolean changeProxy(String proxy) {
        String cmd =
                "reg add "
                        + "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\"
                        + "CurrentVersion\\Internet Settings\""
                        + " /v ProxyServer /t REG_SZ /d "+proxy.trim()+" /f";
        String result = runCMD(cmd, CMD); //результат установки прокси-адреса
        if (result!=null&&result.isEmpty()) {
            cmd = "reg add "
                    + "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\"
                    + "CurrentVersion\\Internet Settings\""
                    + " /v ProxyEnable /t REG_DWORD /d 1 /f";
            result = runCMD(cmd, CMD); //результат активизации прокси
            if (result!=null&&result.isEmpty()) {
                wininet.InternetSetOptionW(0,
                        wininet.INTERNET_OPTION_SETTINGS_CHANGED, null, 0);
                return true;
            } else {return false;}
        } else return false; // ошибка при изменении адреса
    }

    public static String getProxy() {
        String cmd =
                "reg query "
                        + "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\"
                        + "CurrentVersion\\Internet Settings\""
                        + " /v ProxyServer";

        Integer direct = isDirect(); // проверка на активность
        if (direct==null) return null; // ошибка при проверке
        else if (direct.intValue()==0) return "Прокси выключен";
        else {
            String result = runCMD(cmd, QUERY); // получение адреса прокси
            if (result==null) return null; // ошибка при проверке
            else {
                int p = result.indexOf("REG_SZ");
                if (p==-1) return null; // ключ не тот
                return result.substring(p+("REG_SZ").length()).trim();
            }
        }
    }
}
