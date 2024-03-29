package test;

import static org.junit.Assert.*;

import java.awt.Polygon;
import java.net.URL;

import ij.IJ;
import ij.ImagePlus;
import ij.blob.Blob;
import ij.blob.ManyBlobs;

import org.junit.Test;

public class FeatureTest {
	
	@Test
	public void testCustomBlobFeature() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		ExampleBlobFeature test = new ExampleBlobFeature();
		Blob.addCustomFeature(test);
		int a  = 10;
		float c = 1.5f;
		double featurevalue = (Double)mb.get(0).evaluateCustomFeature("myFancyFeature",a,c);
		double diff = mb.get(0).getEnclosedArea()-featurevalue;
		assertEquals(-(c*a-1)*mb.get(0).getEnclosedArea(), diff,0);
		
	}
	
	@Test
	public void testCustomBlobFeature2() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		ExampleBlobFeature test = new ExampleBlobFeature();
		Blob.addCustomFeature(test);
		int a  = 10;
		double c = 1.5;
		int featurevalue = (Integer)mb.get(0).evaluateCustomFeature("mySecondFancyFeature",a,c);
		double diff = mb.get(0).getAreaToPerimeterRatio()-featurevalue;
		assertEquals(-(c*a-1)*mb.get(0).getAreaToPerimeterRatio(), diff,0.5);
	}
	
	@Test
	public void testFilterCustomBlobFeature() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		ExampleBlobFeature test = new ExampleBlobFeature();
		Blob.addCustomFeature(test);
		int a = 10;
		double b = 20;
		ManyBlobs filtered = mb.filterBlobs(6, "myFourthFancyFeature",a,b);
		assertEquals(filtered.size(), 0,0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testFilterCustomBlobFeatureWrongArgument() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		ExampleBlobFeature test = new ExampleBlobFeature();
		Blob.addCustomFeature(test);
		int a = 10;
		int b = 20;
		mb.filterBlobs(6, "myFourthFancyFeature",a,b);
	}
	
	
	@Test
	public void testFilterCustomBlobFeature2() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		ExampleBlobFeature test = new ExampleBlobFeature();
		Blob.addCustomFeature(test);
		ManyBlobs filtered = mb.filterBlobs(4, "myThirdFancyFeature");
		assertEquals(filtered.size(), 1,0);
	}


	@Test
	public void testGetCenterOfGravity() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		int centerx = (int)mb.get(0).getCenterOfGravity().getX();
		int centery = (int)mb.get(0).getCenterOfGravity().getY();
		double diff = Math.abs(48-centerx)+Math.abs(48-centery);
		assertEquals(0, diff,0);
	}

	@Test
	public void testGetElongation() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetMinimumBoundingRectangle() {
		URL url = this.getClass().getResource("rotatedsquare2.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		mb.get(0).getMinimumBoundingRectangle();
		IJ.log("LS " + mb.get(0).getLongSideMBR());
		IJ.log("SS " + mb.get(0).getShortSideMBR());
	}


	@Test
	public void testGetPerimeterCircleRad30() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int peri = (int)(2*Math.PI*29.5);
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(peri, mb.get(0).getPerimeter(),2);
	}

	@Test
	public void testGetPerimeterConvexHull() {
		URL url = this.getClass().getResource("square100x100_minus30x30.png");
		ImagePlus ip = new ImagePlus(url.getPath());
		int periConv = 4*100-4; //400-4(-4 Because the Edges doesnt mutiple counted 
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(periConv, mb.get(0).getPerimeterConvexHull(),2);
	}

	@Test
	public void testEnclosedAreaCircleRad30() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int area = (int)(Math.PI*29.5*29.5);
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(area, mb.get(0).getEnclosedArea(),10);
		
	}
	
	@Test
	public void testFilterEnclosedAreaSqaures() {
		URL url = this.getClass().getResource("squares_20x20_30x30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int areaSmallSquare = (19*19);
		int areaBigSquare = (29*29);
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		ManyBlobs filter = mb.filterBlobs(areaSmallSquare+1, Blob.GETENCLOSEDAREA);
		assertEquals(4, filter.size(),0);
		filter = mb.filterBlobs(areaBigSquare+1, Blob.GETENCLOSEDAREA);
		assertEquals(0, filter.size(),0);
	}

	@Test
	public void testGetCircularity() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		
		double expectedCircularity = 4*Math.PI;
		assertEquals(expectedCircularity, mb.get(0).getCircularity(),0.1);
		
	}

	@Test
	public void testGetThinnesRatio() {
		URL url = this.getClass().getResource("circle_r30.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int circ = 1;
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(circ, mb.get(0).getThinnesRatio(),0.01);
	}

	@Test
	public void testFind4holes() {
		URL url = this.getClass().getResource("nestedObjects.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int holes = 4;
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(holes, mb.get(0).getInnerContours().size(),0);
	}
	
	@Test
	public void testFindThreeBlobs() {
		URL url = this.getClass().getResource("3blobs.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int count = 3;
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(count, mb.size(),0);
	}
	@Test
	public void testNestedFindFiveBlobs() {
		URL url = this.getClass().getResource("nestedObjects.tif");
		ImagePlus ip = new ImagePlus(url.getPath());
		int blobs = 5;
		ManyBlobs mb = new ManyBlobs(ip);
		mb.findConnectedComponents();
		assertEquals(blobs, mb.size(),0);
	}
	
	@Test
	public void testGetOuterContourIsCorrect() {
        //Pfad des Beispielbildes
        URL url = this.getClass().getResource("correctcontour.png");
        //Lade das Beispielbild
        ImagePlus ip = new ImagePlus(url.getPath());
 
        //Analysiere die Blobs
        ManyBlobs mb = new ManyBlobs(ip);
       // mb.setBackground(0);
        mb.findConnectedComponents();
       
        //Die Kontur die ermittelt werden sollte
        int[] xp = {3,4,5,6,7,8,9,10,11,11,11,10,9,8,7,6,5,4,3,2,2,2,3};
        int[] yp = {1,1,2,2,2,1,1,2,2,3,4,4,5,5,4,4,4,5,5,4,3,2,1};
       
        //Die Kontur die ermittelt wurde
        Polygon contour = mb.get(0).getOuterContour();
       
        //Differenz der beiden Konturen
        int diff=0;
        for(int i = 0; i < contour.npoints; i++){
                diff += Math.abs(contour.xpoints[i] - xp[i]) + Math.abs(contour.ypoints[i] - yp[i]);
        }
       
        //Überprüfe ob die Differenz 0 ergibt. Dann sind beide Konturen gleich.
        assertEquals(0,diff,0);
	}

}
