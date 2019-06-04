import java.io.* ;
import java.util.* ;

public class Pacman {
    static boolean mark[][] = new boolean [105][105] ;
    static ArrayList <Cell> dots = new ArrayList<>() ;

    static Cell bestCell (int row, int col, char[][] board) {
        int dx[] = {-1,0 ,+1, 0} ;
        int dy[] = {0 ,-1, 0, +1} ;
        int mi = Integer.MAX_VALUE ;
        Cell res=null ;
        for (int i=0 ; i<4 ; ++i) {
            if (row+dx[i]<board.length && row+dx[i]>=0 && col+dy[i]<board[0].length && col+dy[i]>=0 &&
                    (board[row+dx[i]][col+dy[i]]=='.' || board[row+dx[i]][col+dy[i]]==' ')
                    && !mark[row+dx[i]][col+dy[i]]) {
                int mi1 = Integer.MAX_VALUE ;
                for (Cell it : dots) {
                    if (!mark[it.getRow()][it.getCol()])
                        mi1 = Math.min(mi1, Math.abs(row+dx[i]-it.getRow()) + Math.abs(col+dy[i]-it.getCol())) ;
                }
                if (mi1<mi) {
                    mi = mi1 ;
                    res = new Cell (row+dx[i], col+dy[i]) ;
                }
            }
        }
        return res ;
    }

    static void dfs(int row, int col, char[][] board) {
        mark[row][col] = true ;

        Cell next = bestCell(row, col, board) ;
        if (next != null) {
            if (board[next.getRow()][next.getCol()]=='.') {
                if (next.getRow()==row && next.getCol()>col) board[next.getRow()][next.getCol()] = '>' ;
                if (next.getRow()==row && next.getCol()<col) board[next.getRow()][next.getCol()] = '<' ;
                if (next.getRow()>row && next.getCol()==col) board[next.getRow()][next.getCol()] = 'v' ;
                if (next.getRow()<row && next.getCol()==col) board[next.getRow()][next.getCol()] = '^' ;
            }
            dfs(next.getRow(), next.getCol(), board) ;
        }
    }

    static void bfs(int row, int col, char[][] board) {
        for (int i=0 ; i<105 ; ++i)
            for (int j=0 ; j<105 ; ++j)
                mark[i][j] = false ;

        LinkedList <Cell> queue = new LinkedList<>();
        mark[row][col] = true ;
        queue.add(new Cell(row, col)) ;
        while (queue.size() > 0) {
            Cell now = queue.poll() ;
            Cell go = bestCell(now.getRow(), now.getCol(), board) ;
            if (go == null) return ;
            if (board[go.getRow()][go.getCol()]=='.') {
                if (go.getRow()==now.getRow() && go.getCol()>now.getCol()) board[go.getRow()][go.getCol()] = '>' ;
                if (go.getRow()==now.getRow() && go.getCol()<now.getCol()) board[go.getRow()][go.getCol()] = '<' ;
                if (go.getRow()>now.getRow() && go.getCol()==now.getCol()) board[go.getRow()][go.getCol()] = 'v' ;
                if (go.getRow()<now.getRow() && go.getCol()==now.getCol()) board[go.getRow()][go.getCol()] = '^' ;
            }
            mark[go.getRow()][go.getCol()] = true ;
            queue.add(go) ;
        }
    }

    public static void main(String[] args) throws Exception {
        File input = new File("2.lay") ;
        Scanner in = new Scanner (input) ;
        ArrayList <String> layout = new ArrayList<>() ;
        while (in.hasNext()) {
            layout.add(in.nextLine()) ;
        }
        int rowNumber=layout.size(), colNumber=layout.get(0).length() ;
        char myLayout[][] = new char[rowNumber][colNumber] ;
        int j=0 ;
        for (String it : layout) {
            myLayout[j] = it.toCharArray() ;
            j++ ;
        }
        int startRow=0, startCol=0 ;
        for (int i=0 ; i<rowNumber ; ++i) {
            for (int j1=0 ; j1<colNumber ; ++j1) {
                if (myLayout[i][j1]=='P') {
                    startRow = i ;
                    startCol = j1 ;
                }
                if (myLayout[i][j1]=='.') {
                    dots.add(new Cell(i, j1)) ;
                }
            }
        }
        in = new Scanner(System.in) ;
        System.out.print("Plaese enter 1 or 2 for dfs or bfs : \n") ;
        System.out.print("1. dfs\n2. bfs\n") ;
        int tmp = in.nextInt() ;
        if (tmp==1) dfs(startRow, startCol, myLayout) ;
        if (tmp==2) bfs(startRow, startCol, myLayout) ;
        for (int i=0 ; i<rowNumber ; ++i) {
            for (int j1=0 ; j1<colNumber ; ++j1) {
                System.out.print(myLayout[i][j1]) ;
            }
            System.out.println() ;
        }
        File output = new File("output.lay") ;
        FileWriter fw = new FileWriter(output) ;
        BufferedWriter bw = new BufferedWriter(fw) ;
        PrintWriter out = new PrintWriter (bw) ;
        for (int i=0 ; i<rowNumber ; ++i) {
            for (int j1=0 ; j1<colNumber ; ++j1) {
                out.print(myLayout[i][j1]) ;
            }
            out.println() ;
        }
        out.close();
    }

}


