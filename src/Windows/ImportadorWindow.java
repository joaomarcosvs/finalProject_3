package Windows;

import DataBase.Dao.*;
import DataBase.Model.Cursos;
import DataBase.Model.Disciplinas;
import DataBase.Model.Fases;
import DataBase.Model.Professores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.Connection;
import java.util.HashMap;

public class ImportadorWindow extends JFrame {

    private JFileChooser fc;
    private JTextField txtArquivo;
    private JButton btnImportar;
    private Connection conexao;

    private HashMap<String, String> hmpDisciplinas = new HashMap<>();
    {
        hmpDisciplinas.put("27696", "Evolução da Tecnologia");
        hmpDisciplinas.put("27697", "Sistemas Eletrônicos");
        hmpDisciplinas.put("27695", "Fundamentos Matematicos");
        hmpDisciplinas.put("27694", "Resoluções de Problemas Computacionais");
        hmpDisciplinas.put("27701", "Laboratório de Programação");
        hmpDisciplinas.put("27702", "Desenvolvimento de aplicações I");
        hmpDisciplinas.put("27699", "Design de Interação");
        hmpDisciplinas.put("27698", "Lógica Computacional");
        hmpDisciplinas.put("27023", "Funções e Derivadas");
        hmpDisciplinas.put("27700", "Sistemas Digitais");
        hmpDisciplinas.put("27300", "Cálculo Integral");
        hmpDisciplinas.put("27706", "Programação Orientada a Objetos");
        hmpDisciplinas.put("27703", "Gerenciamento de Dados I");
        hmpDisciplinas.put("27705", "Arquitetura de Computadores");
        hmpDisciplinas.put("27704", "Resolução de Problemas Estruturados I");
        hmpDisciplinas.put("27709", "Desenvolvimento de Aplicações II");
        hmpDisciplinas.put("27711", "Resolução de Problemas Estruturados II");
        hmpDisciplinas.put("27707", "Projeto e Otimização de Algoritmos");
        hmpDisciplinas.put("27708", "Gerenciamento de Dados II");
        hmpDisciplinas.put("27710", "Sistemas Operacionais");
        hmpDisciplinas.put("23231", "Projeto Integrador I");
        hmpDisciplinas.put("27715", "Segurança da Informação");
        hmpDisciplinas.put("27713", "Teoria de Grafos");
        hmpDisciplinas.put("27714", "Arquitetura de Redes I");
        hmpDisciplinas.put("27712", "Engenharia de Software I");
        hmpDisciplinas.put("27716", "Desenvolvimento para Dispositivos Móveis");
        hmpDisciplinas.put("27720", "Engenharia de Software II");
        hmpDisciplinas.put("27719", "Internet das Coisas");
        hmpDisciplinas.put("27721", "Projeto em Computação");
        hmpDisciplinas.put("27717", "Linguagens Formais e Autômatos ");
        hmpDisciplinas.put("27718", "Arquitetura de Redes II");
        hmpDisciplinas.put("23277", "Projeto Integrador II");
        hmpDisciplinas.put("27729", "Computação Paralela e Distribuída ");
        hmpDisciplinas.put("27727", "Inteligência Artificial");
        hmpDisciplinas.put("27731", "Realidade Virtual e Aumentada");
        hmpDisciplinas.put("27730", "Projeto Inovador I");
        hmpDisciplinas.put("27728", "Compiladores");
        hmpDisciplinas.put("23279", "TCC II");
        hmpDisciplinas.put("23248", "Internet das Coisas");
        hmpDisciplinas.put("27736", "Data Science");
        hmpDisciplinas.put("23246", "Análise de Dados");
        hmpDisciplinas.put("23255", "Objetos Distribuídos e Serviços Web");
        hmpDisciplinas.put("23280", "TCC III");
        hmpDisciplinas.put("23251", "Tópicos Especiais");
        hmpDisciplinas.put("23250", "Gerenciamento de Projetos");


    }

    private HashMap<String, String> hmpDiaDaSemana = new HashMap<>();
    {
        hmpDiaDaSemana.put("01", "Domingo");
        hmpDiaDaSemana.put("02", "Segunda");
        hmpDiaDaSemana.put("03", "Terça");
        hmpDiaDaSemana.put("04", "Quarta");
        hmpDiaDaSemana.put("05", "Quinta");
        hmpDiaDaSemana.put("06", "Sexta");
        hmpDiaDaSemana.put("07", "Sabado");
    }

    private HashMap<String, String> hmpTitulo = new HashMap<>();
    {
        hmpTitulo.put("01", "Pós-Graduação");
        hmpTitulo.put("02", "Mestrado");
        hmpTitulo.put("03", "Doutorado");
        hmpTitulo.put("04", "Pós-Doutorado");
    }

    public ImportadorWindow(Connection conexao) {
        this.conexao = conexao;
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Importador");
        setResizable(false);
        setLayout(null);
        componentesCriar();
    }

    private JPanel painelCabecalho;
    private JLabel lblCabecalho;

    private void componentesCriar() {
        txtArquivo = new JTextField();
        txtArquivo.setBounds(120, 10, 350, 20);
        txtArquivo.setEditable(false);
        getContentPane().add(txtArquivo);

        btnImportar = new JButton(new AbstractAction("Importar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setMultiSelectionEnabled(false);
                fc.setDialogTitle("Importar");
                fc.setAcceptAllFileFilterUsed(false);
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File arquivo = fc.getSelectedFile();
                    txtArquivo.setText(arquivo.getAbsolutePath());
                    importarArquivo(arquivo);
                }


            }

        });
        btnImportar.setBounds(245, 50, 100, 30);
        getContentPane().add(btnImportar);

        painelCabecalho = new JPanel();
        painelCabecalho.setBounds(0, 110, 600, 50);
        painelCabecalho.setLayout(null);
        painelCabecalho.setBackground(new Color(60, 120, 180));
        getContentPane().add(painelCabecalho);

        lblCabecalho = new JLabel("Editar CRUD");
        lblCabecalho.setBounds(240, 10, 270, 25);
        lblCabecalho.setForeground(Color.white);
        lblCabecalho.setFont(new Font("SansSerif", Font.BOLD, 18));
        painelCabecalho.add(lblCabecalho);

        JButton btnCursos = new JButton(new AbstractAction("Cursos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                CursosWindow cursosWindow = new CursosWindow(conexao);
                cursosWindow.setVisible(true);
            }
        });
        btnCursos.setBounds(220, 170, 150, 30);
        getContentPane().add(btnCursos);

        JButton btnDisciplinas  = new JButton(new AbstractAction("Disciplinas") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisciplinasWindow disciplinasWindow = new DisciplinasWindow(conexao);
                disciplinasWindow.setVisible(true);
            }
        });
        btnDisciplinas.setBounds(220, 210, 150, 30);
        getContentPane().add(btnDisciplinas);

        JButton btnProfessores = new JButton(new AbstractAction("Professores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfessoresWindow professoresWindow = new ProfessoresWindow(conexao);
                professoresWindow.setVisible(true);
            }
        });
        btnProfessores.setBounds(220, 250, 150, 30);
        getContentPane().add(btnProfessores);

        JButton btnFases = new JButton(new AbstractAction("Fases") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FasesWindow fasesWindow = new FasesWindow(conexao);
                fasesWindow.setVisible(true);
            }
        });
        btnFases.setBounds(220, 290, 150, 30);
        getContentPane().add(btnFases);


    }

    private int idCursoAtual;
    private int idFaseAtual;

    private void importarArquivo(File arquivo) {
        try {

            InputStream is = new FileInputStream(arquivo);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String linha = null;
            int idDisciplinaAtual = -1;

            while ((linha = buffer.readLine()) != null) {
                System.out.println(linha);
                if (linha.startsWith("0")) {
                    Cursos c = new Cursos();
                    c.setNomeCurso(linha.substring(1, 22));
                    c.setFaseInicial(Integer.parseInt(linha.substring(58, 60)));
                    c.setFaseFinal(Integer.parseInt(linha.substring(65, 67)));

                    CursosDAO cursosDAO = new CursosDAO(conexao);
                    idCursoAtual = cursosDAO.insert(c);
//                    System.out.println("Curso cadastrado com ID: " + idCursoAtual);

                }

                if (linha.startsWith("1")) {
                    Fases f = new Fases();
                    f.setNumeroFase(Integer.parseInt(linha.substring(1, 3)));
                    f.setIdCursos(idCursoAtual);

                    FasesDAO fasesDAO = new FasesDAO(conexao);
                    idFaseAtual = fasesDAO.insert(f);
//                    System.out.println("Fase cadastrado com ID: " + idFaseAtual);
                }

                if (linha.startsWith("2")) {
                    Disciplinas d = new Disciplinas();
                    String codigoDisc = linha.substring(1, 6);
                    d.setCodigoDisciplina(Integer.parseInt(codigoDisc));

                    String nomeDisciplina = hmpDisciplinas.get(codigoDisc);
                    if (nomeDisciplina == null) {
//                        System.out.println("Código de disciplina não encontrado no HashMap: " + codigoDisc);
                        continue;
                    }
                    d.setNomeDisciplina(nomeDisciplina);

                    String codDia = linha.substring(7, 9).trim();
                    String nomeDia = hmpDiaDaSemana.get(codDia);
                    d.setDiaSemana(nomeDia);

                    d.setQuantProfessores(Integer.parseInt(linha.substring(10, 11)));
                    d.setIdFases(idFaseAtual);

                    DisciplinasDAO disciplinasDAO = new DisciplinasDAO(conexao);
                    idDisciplinaAtual = disciplinasDAO.insert(d);
                }


                if (linha.startsWith("3")) {
                    Professores p = new Professores();
                    p.setNomeProfessor(linha.substring(1, 41).trim());
                    String codTitulo = linha.substring(41, 43).trim();
                    String t = hmpTitulo.get(codTitulo);
                    p.setTitulo(t);

                    int idProfessorAtual = -1;
                    ProfessoresDAO professoresDAO = new ProfessoresDAO(conexao);
                    try {
                        idProfessorAtual = professoresDAO.insertAndReturnId(p);
                    } catch (Exception ex) {
//                        System.out.println("Professor duplicado ignorado: " + p.getNomeProfessor());
                        try {
                            idProfessorAtual = professoresDAO.selectIdByNome(p.getNomeProfessor());
                        } catch (Exception ex2) {
//                            System.out.println("Erro ao buscar ID do professor existente: " + p.getNomeProfessor());
                        }
                    }
                    if (idDisciplinaAtual > 0 && idProfessorAtual > 0) {
                        DisciplinasProfessoresDAO dpDAO = new DisciplinasProfessoresDAO(conexao);
                        dpDAO.insert(idDisciplinaAtual, idProfessorAtual);
                    } else {
//                        System.out.println("Não foi possível vincular disciplina e professor: IDs inválidos.");
                    }
                }

            }
            buffer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
