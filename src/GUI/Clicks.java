package GUI;

import LCA.LCAsolver;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.util.MouseManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Clicks{//implements ViewerListener {
    protected boolean loop = true;
    private final Graph graph;
    private final LCAsolver lcAsolver;
    private int[] selectedId=new int[2];
    private int selectedIndex=0;
    private int LCAid = 0;
    public Clicks(Graph graph, LCAsolver lcAsolver) {
        this.lcAsolver=lcAsolver;
        this.graph = graph;
        this.graph.setAttribute("ui.stylesheet", "node {fill-color: grey;size: 10px;stroke-mode: plain;stroke-color: black;stroke-width: 1px;}node.important{ fill-color: red;}node.big{fill-color: blue;}node.small{fill-color: orange;}");
        this.graph.getNode("0").setAttribute("ui.class","big");
        Viewer viewer = this.graph.display();
        View view=viewer.getDefaultView();
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Button pushed on node ");
                Element curElement = view.findNodeOrSpriteAt(e.getX(),e.getY());
                if (curElement != null) {
                    String id=curElement.getId();
                    if(graph.getNode(id) != null) {
                        System.out.println("Clicked node "+id);
                        Node node = graph.getNode(id);
                        graph.getNode(Integer.toString(selectedId[selectedIndex])).removeAttribute("ui.class");
                        node.removeAttribute("ui.class" );
                        node.setAttribute("ui.class", "important");
                        selectedId[selectedIndex]=Integer.parseInt(id);
                        int LCA = lcAsolver.LCA(selectedId[0], selectedId[1]); int LCA2 = lcAsolver.bruteForceLCA(selectedId[0], selectedId[1]);
                        System.out.println("Selected: "+selectedId[0]+" and " + selectedId[1]+ ", LCA="+LCA);
                        if(LCA !=LCA2){
                            throw new RuntimeException("Computational Error! RMQ-LCA and BF-LCA are: "+LCA+" "+LCA2);
                        }
                        graph.getNode(Integer.toString(LCAid)).removeAttribute("ui.class");
                        graph.getNode(Integer.toString(LCA)).removeAttribute("ui.class");
                        graph.getNode(Integer.toString(LCA)).setAttribute("ui.class", "big");
                        LCAid=LCA;
                        selectedIndex=1-selectedIndex;
                        graph.getNode("0").setAttribute("ui.class","small");
                        lcAsolver.printTime();
                    }
                }
            }

    });
        view.setMouseManager(new MouseManager(){
                    @Override
                    public void mouseClicked(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                    @Override
                    public void mouseDragged(MouseEvent e) {}
                    @Override
                    public void mouseMoved(MouseEvent e) {}
                    @Override
                    public void init(GraphicGraph graph, View view) {}
                    @Override
                    public void release() {}
                });
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        //ViewerPipe fromViewer = viewer.newViewerPipe();
        //fromViewer.addViewerListener(this);
        //fromViewer.addSink(this.graph);

        //while(loop) {
            //fromViewer.pump();
        //}
    }

   // public void viewClosed(String id) {
        //loop = false;
    //}
    /*public void buttonPushed(String id) {

        System.out.println("Button pushed on node "+id);
        Node node = this.graph.getNode(id);
        node.removeAttribute("ui.class" );
        node.setAttribute("ui.class", "important");
        this.graph.getNode(Integer.toString(selectedId[selectedIndex])).removeAttribute("ui.class");
        selectedId[selectedIndex]=Integer.parseInt(id);
        int LCA = lcAsolver.LCA(selectedId[0], selectedId[1]);
        this.graph.getNode(Integer.toString(LCAid)).removeAttribute("ui.class");
        this.graph.getNode(Integer.toString(LCA)).setAttribute("ui.class", "LCA");
        LCAid=LCA;
        selectedIndex=1-selectedIndex;
        System.out.println("Selected: "+selectedId[0]+" and " + selectedId[1]+ ", LCA="+LCA);
    }
    public void buttonReleased(String id) {
        System.out.println("Button released on node "+id);
    }*/
}