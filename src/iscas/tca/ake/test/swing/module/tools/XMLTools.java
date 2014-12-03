package iscas.tca.ake.test.swing.module.tools;

import static iscas.tca.ake.test.swing.module.tools.UtilMy.print;
import static iscas.tca.ake.test.swing.module.tools.UtilMy.printnb;

import java.io.File;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class XMLTools {
	private Document doc;
	String filePath;
	public XMLTools(){
		
	}
	// route can not multidefy
	
//	private void traverse(Map<String, String>){
//		
//	}
	public XMLTools(String filePath, String rootTag){
		try{
			openXML(filePath, rootTag);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// open the XML
	public  XMLTools openXML(String filePath, String rootTag) throws Exception {
		File f = new File(filePath);
		doc = null;
		this.filePath = filePath;
		//f.getAbsolutePath()
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			Element e = doc.createElement(rootTag);
			doc.appendChild(e);
			this.writeToFile();
		} else {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(f);
		}
		doc.normalize();
		doc.normalizeDocument();
		return this;
	}

	public void writeToFile(String filePath) throws Exception {
		File f = new File(filePath);
		Source src = (Source) new DOMSource(doc);
		Result rlt = new StreamResult(f);
		Transformer tr = TransformerFactory.newInstance().newTransformer();
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		tr.setOutputProperty(OutputKeys.ENCODING, "gbk");
		tr.transform(src, rlt);
	}

	// travel the xml
	public void showXML() {
		doc.normalize();
		travel(doc.getDocumentElement());
	}

	private void printNode(Node root) {
		printnb("<" + root.getNodeName());
		NamedNodeMap nnm = root.getAttributes();
		if (nnm != null) {
			for (int i = 0; i < nnm.getLength(); i++) {
				printnb(" "+nnm.item(i).getNodeName() + ":"
						+ nnm.item(i).getNodeValue());
			}
		}
		print(">");
	}

	private void travel(Node root) {
		if (root.hasChildNodes()) {
			printNode(root);
			NodeList nl = root.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {

				travel(nl.item(i));
			}
		} else if (root.getNodeType() == Node.TEXT_NODE) {
			if (root.getNodeValue().trim().length() != 0) {
				printnb(root.getNodeValue());
				print("</"+root.getParentNode().getNodeName()+">");
			}
		}

	}
//º”»Îtext Node[tagName, value]
	public Node appendTextElement(Node pNode, String tagName, String value) {
		Element child = doc.createElement(tagName);
		Text textNode = doc.createTextNode(value);
		child.appendChild(textNode);
		pNode.appendChild(child);
		return textNode;
	}
	
	public Node appendElement(Node pNode, String... tagNamePath){
		Node parent = pNode;
		Node sub = null;
		for(int i=0; i<tagNamePath.length; i++){
			sub= doc.createElement(tagNamePath[i]);
			parent.appendChild(sub);
			parent = sub;
		}
		return sub;
	}
	@Test
	//public void testAdd()
	public Node appendElement(Node pNode, String tagName, String[] attrNames,
			String[] attrValues) {
		Element elem = this.doc.createElement(tagName);

		if (attrNames != null && attrValues != null) {
			
			for (int i = 0; i < attrNames.length; i++) {
				Attr a = doc.createAttribute(attrNames[i]);
				a.setNodeValue(attrValues[i]);
				elem.setAttributeNode(a);
				// set the id
				if (attrNames[i].equalsIgnoreCase("id")) {
					elem.setIdAttributeNode(a, true);
				}
			}
		}
		pNode.appendChild(elem);
		return elem;
	}
	//find out if the endpoint hanged in the parentPath
	
	//from the root find the node
	
	public Document getDocument(){
		return this.doc;
	}
	public String[] getAllTagValues(String tagName){
		NodeList nl = doc.getElementsByTagName(tagName);
		String[] values = new String[nl.getLength()];
		for(int i = 0; i<nl.getLength(); i++){
			values[i] = (nl.item(i).getFirstChild().getNodeValue());
		}
		return values;
	}
	
	public void writeToFile()throws Exception{
		this.writeToFile(filePath);
	}
	
	//find the first Node that matches the tag !!!!!!!!
	//start 
	public Node setNode(String[] tagPath, String value){
		//Down to Up
		Node elem = findEndNode(tagPath);
		// Up to Down
		if(elem==null) {
			NodeInPath nip = findInnerNode(doc.getDocumentElement(), tagPath);
			elem = nip.pNode;
			elem = appendElement(elem, Arrays.copyOfRange(tagPath, nip.order, tagPath.length, tagPath.getClass()));
			Node tNode = doc.createTextNode(value);
			elem.appendChild(tNode);
		}
		else{
			elem.getChildNodes().item(0).setNodeValue(value);
		}
		return elem;
	}
	

	//down to up
	public String getEndNodeValue(Node pNode, String...tagNamePath){
		pNode.normalize();
		NodeInPath nip = findInnerNode(pNode, tagNamePath);
		if(nip.order == tagNamePath.length){
			return nip.pNode.getFirstChild().getNodeValue();
		}
		return null;
	}
	//down to up
	public String getEndNodeValue(String... tagPath){
		Node endNode = findEndNode(tagPath);
		if(endNode!=null)
			return endNode.getFirstChild().getNodeValue();
		return null;
	}
	
	//Up to Down - Down to Up
	private Node findEndNode(String... tagPath){
		NodeInPath nip = findInnerNode(doc.getDocumentElement(), tagPath);
		if(nip.order==tagPath.length){
			return nip.pNode;
		}
		//down to up
		int len = tagPath.length;
		NodeList nl = this.doc.getElementsByTagName(tagPath[len-1]);
		for(int i=0; i<nl.getLength(); i++){
			Node node = nl.item(i);
			if(isNodeInPath(node, tagPath)){
				return node;
			}
		}
		return null;
	}
	
	//up to down,  getNodeValue from the root pNode to search	
	private NodeInPath findInnerNode(Node root, String[] tagPath){
		NodeList nl = root.getChildNodes();
		int i =0;
		NodeInPath nip = new NodeInPath(0, root);
		while(nip.order<tagPath.length && i<nl.getLength()){
			if(nl.item(i).getNodeName().equals(tagPath[nip.order])){
				nip.order++;
				nip.pNode = nl.item(i);
				i = 0;
				//System.out.print(nip.pNode.getChildNodes().getLength());
				nl = nip.pNode.getChildNodes();
				//System.out.print(nl.item(i).getNodeName()+"--");
			}
			else{i++;}//System.out.println(nl.getLength());}
		}
			return nip;
	}
		
		class NodeInPath{
			//pNode->path[order]->[path[order+1]]
			int order;
			Node pNode;
			public NodeInPath(int order, Node n){
				this.order = order;
				this.pNode = n;
			}
		}
		
		
	private boolean isNodeInPath(Node endNode, String... parentPath){
		for(int i=parentPath.length-1; i>=0; i--){
			if(!endNode.getNodeName().equals(parentPath[i]))
				return false;
			endNode = endNode.getParentNode();
		}
		return true;
	}

	
}
