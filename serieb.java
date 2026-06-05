import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class Time {
    String nome;
    int pontos;
    int golsFeitos;
    int golsSofridos;

    Time(String nome) {
        this.nome = nome;
        this.pontos = 0;
        this.golsFeitos = 0;
        this.golsSofridos = 0;
    }

    int saldo() {
        return golsFeitos - golsSofridos;
    }
}

public class serieb {

    static ArrayList<Time> tabela = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("jogos.txt"));
        String linha;

        while ((linha = br.readLine()) != null) {

            if (linha.trim().equals("")) {
                continue;
            }

            String[] partes = linha.split(",");

            String timeA = partes[1].trim();
            String timeB = partes[2].trim();
            String resultado = partes[3].trim();

            String[] gols = resultado.split("x");

            int golsA = Integer.parseInt(gols[0]);
            int golsB = Integer.parseInt(gols[1]);

            Time a = pegarTime(timeA);
            Time b = pegarTime(timeB);

            a.golsFeitos += golsA;
            a.golsSofridos += golsB;

            b.golsFeitos += golsB;
            b.golsSofridos += golsA;

            if (golsA > golsB) {
                a.pontos += 3;
            } else if (golsB > golsA) {
                b.pontos += 3;
            } else {
                a.pontos += 1;
                b.pontos += 1;
            }
        }

        br.close();

        tabela.sort((t1, t2) -> {
            if (t2.pontos != t1.pontos) {
                return t2.pontos - t1.pontos;
            }
            return t2.saldo() - t1.saldo();
        });

        System.out.println("+-----+----------------------+--------+-------+");
        System.out.println("| #   | Time                 | Pontos | Saldo |");
        System.out.println("+-----+----------------------+--------+-------+");

        for (int i = 0; i < tabela.size(); i++) {
            Time t = tabela.get(i);

            System.out.printf("| %-3s | %-20s | %-6d | %-5d |\n",
                    (i + 1) + "º", t.nome, t.pontos, t.saldo());
        }

        System.out.println("+-----+----------------------+--------+-------+");
    }

    static Time pegarTime(String nome) {
        for (int i = 0; i < tabela.size(); i++) {
            if (tabela.get(i).nome.equals(nome)) {
                return tabela.get(i);
            }
        }

        Time novo = new Time(nome);
        tabela.add(novo);
        return novo;
    }
}
