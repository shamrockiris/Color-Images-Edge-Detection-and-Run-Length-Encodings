/**
@author Xiaowen Wang
*/

public class RunLengthEncoding implements Iterable {

    private DList rgbValue;
    private int width;
    private int height;

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
      DList rgbValue = new DList();
      int repeat = width * height;
      rgbValue.insertEnd(0,0,0,repeat);
      this.rgbValue = rgbValue;
      this.width = width;
      this.height = height;
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
      DList rgbValue = new DList();
      for(int i = 0; i < runLengths.length; i++) {
          rgbValue.insertEnd(red[i],green[i],blue[i],runLengths[i]);
      }
      this.width = width;
      this.height = height;
      this.rgbValue = rgbValue;
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    return this.width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    return new RunIterator(rgbValue,rgbValue.head);
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
      PixImage pic = new PixImage(this.width,this.height);
      int x = 0;
      int y = 0;
      RunIterator run = iterator();
      while(run.hasNext()){
          int[] dot = new int[4];
          dot = run.next();
          for(int i = 0; i < dot[0]; i++) {
              pic.setPixel(x,y,(short) dot[1],(short) dot[2], (short) dot[3]);
              if(x < width){
                  x++;
              } if(x == width) {
                  x = 0;
                  y++;
              }
          }
      }

    return pic;
  }


  public String toString() {
      StringBuilder list = new StringBuilder();
      for(RunIterator run = iterator(); run.hasNext();){
          int[] pix = new int[4];
          pix = run.next();
          list.append("[" + pix[0] + "," + pix[1] + "," + pix[2] + "," + pix[3] + "]" + " ");
      }
    return list.toString();
  }


  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
      this.width = image.getWidth();
      this.height = image.getHeight();
      short current_red = image.getRed(0,0);
      short current_green = image.getGreen(0,0);
      short current_blue = image.getBlue(0,0);
      DList rgbValue = new DList();
      int count = 0;
      for(int j = 0; j < height; j++) {
          for(int i = 0; i < width; i++) {
              if(current_red == image.getRed(i,j) && current_green == image.getGreen(i,j) && current_blue == image.getBlue(i,j)) {
                  count++;
              } else {
                  rgbValue.insertEnd(current_red, current_green, current_blue,count);
                  current_red = image.getRed(i,j);
                  current_green = image.getGreen(i,j);
                  current_blue = image.getBlue(i,j);
                  count = 1;
              }
          }
      }
      rgbValue.insertEnd(current_red,current_green,current_blue,count);
      this.rgbValue = rgbValue;

    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
      int[] cur = new int[4];
      int[] pre = {-1, -1, -1, -1};
      int size = 0;
      for(RunIterator run = iterator(); run.hasNext();) {
          cur = run.next();
          if(pre[1] == cur[1] && pre[2] == cur[2] && pre[3] == cur[3]) {
              System.out.println("Illegal run!");
          }
          size = size + cur[0];
          if(cur[0] < 1) {
              System.out.println("Too short!");
          }
          pre = cur;
      }

      if(size != width * height) {
          System.out.println("Size incorrect!");
      }

  }


  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
      // position shows where the node if all the pix are in a line
      int position = y * width + x + 1;
      for(RunIterator run = iterator(); run.hasNext();){
          int[] pix = new int[4];
          DListNode current = run.getNode();
          pix = run.next();
          position = position - pix[0];
          // position ends as 0 means the pix in located at the end of the node of the linked list
          if(position == 0){
              // if the new value equals the original one , we don't need to do anything
              if(current.red == red && current.green == green && current.blue == blue) {
                  break;
              }
              // if there are more than one pix in this node of linked list
              if(pix[0] > 1) {
                  pix[0] = pix[0] - 1;
                  current.repeat = pix[0];
                  current.red = pix[1];
                  current.green = pix[2];
                  current.blue = pix[3];
                  this.rgbValue.insertAfter(red, green, blue, 1, current);
                  break;
              } else if(pix[0] == 1) { // there is only one pix in this node of linked list
                  if (current.prev != null && current.next != null){ //if the previous node and next node has the same rgb value as the new value
                      if(current.prev.red == current.next.red && current.prev.green == current.next.green && current.prev.blue == current.next.blue) {
                      current.prev.repeat = current.prev.repeat + current.next.repeat + 1;
                      this.rgbValue.deleteNode(current.next);
                      this.rgbValue.deleteNode(current);
                      break;
                  }
                  }
                  if(current.prev != null) {
                      if (current.prev.red == red && current.prev.green == green && current.prev.blue == blue) { //if only previous node has the same rgb value as the new value
                          current.prev.repeat = current.prev.repeat + 1;
                          this.rgbValue.deleteNode(current);
                          break;
                      }
                  }
                  if (current.next != null) {
                          if (current.next.red == red && current.next.green == green && current.next.blue == blue) { // if only the next node has the same rgb value as the new value
                              current.next.repeat = current.next.repeat +1;
                              this.rgbValue.deleteNode(current);
                              break;
                              }
                          }
                  // new rgb value is different from previous and next node
                  current.setNode(red,green,blue,1);
                  break;
              }
          }
          // position ends as negative value means the pix located in middle or start of the this node
          if(position < 0) {
              if(current.red == red && current.green == green && current.blue == blue){
                  break;
              }
              pix[0] = pix[0] + position - 1;
              if(pix[0] != 0) { // pix located in middle
                  current.repeat = pix[0];
                  current.red = pix[1];
                  current.green = pix[2];
                  current.blue = pix[3];
                  this.rgbValue.insertAfter(red, green, blue, 1, current);
                  current = current.next;
                  this.rgbValue.insertAfter((short) pix[1], (short) pix[2], (short) pix[3], -position, current);
                  break;
              } else { // pix located in start
                  current.repeat = -position;
                  this.rgbValue.insertBefore(red,green,blue,1,current);
                  break;
              }

          }
      }
    check();
  }


  /**
   * TEST CODE
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
//      System.out.print(rle1.toString());
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
//      System.out.print(rle1.toString());
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
//      System.out.print(rle1.toString());
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
//      System.out.print(rle1.toString());
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
//      System.out.print(rle1.toString());
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
//      System.out.print(rle1.toString());
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
//      System.out.print(rle1.toString());
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
