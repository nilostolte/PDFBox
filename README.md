# PDFBox

The [**Apache PDFBox library**](https://pdfbox.apache.org) is an open source Java tool for working with PDF documents. The Apache PDFBox library project allows viewing PDF documents, creation of new PDF documents, manipulation of existing documents and the ability to extract content from documents. Apache PDFBox also includes several command-line utilities.

The project in this repository offers several versions of [PDFBox](https://pdfbox.apache.org) **source code** that can be directly compiled with Eclipse without using Maven. The source code version used here is `pdfbox-2.0.23`. [The complete version](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete) (**PDFBox-Complete**) is a complete unmodified PDFBox with all packages. The other versions, which are in other repositories for convenience, are modified versions offering more capabilities and generally for more specific uses.

## Overview

### Compiling Projects Using Eclipse

The advantage of these repositories is that no building software is necessary (no need for Maven), thus one doesn't even need to be a programmer to compile and to run the examples. One can easily compile the contents of these repositories using Eclipse for Java, for example. 

#### Creating Workspaces 

When starting Eclipse it always asks for the path of the Workspace. A Workspace is just a directory where the projects are stored. It is highly recommended to put a complete path starting with the disk where one wants to store the projects. Although it is not very difficult to move a project from a place to another, it is easy to get lost if one has several workspaces in different disks or different paths. Putting several workspaces under the same directory is a good idea because it is easy to remember where they are, and it is also easier to make backups. It is recommended to maintain several workspaces instead of only one with all projects inside. If possible it is better to have one workspace per project, especially when it is a big project. One can have other small projects with the main project if they are fairly small and if they are tightly related to the main project. 

#### Finding Files and Directories

If one is willing to access the directory where the project or a package is located there is a very simple and easy way to do it in Eclipse. Just click at the file, project or package one is trying to see in the file system and then right-click choosing **"Properties"**. A window will open and by clicking in the indicated icon a Window explorer (if one is working on Windows) window  will open at the directory where it is located:

<br>

![image](https://user-images.githubusercontent.com/80269251/114750182-b28d0500-9d21-11eb-8db8-21c33ef48ff2.png)

<br>

In the example above one clicked at [**`src`**](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete/src), the root of the source files in Eclipse. Once the file explorer opens one can dismiss the Properties window.

#### Compiling

To do that, just create a new Java project in Eclipse (**"File > New > Java Project"**) and then copy all the files in the source code under the directory [**`src`**](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete/src) to the directory **`src`** of your project. Then, just click on **"File > Refresh"** and Eclipse starts compiling the code. Once the code is compiled you can already run the examples. Whenever one refreshes a Workspace in Eclipse, whatever new `java` file found is compiled and the compiled file is saved in the bin directory in a same package storage hierarchy as the source code. Whenever a file is not a java file, it is just copied from **`src`** to **`bin`**. This is basically what happens when using Eclipse. Any new Java file created is automatically compiled on the fly. This simplicity and all the help when typing new code is what makes the beauty of Eclipse IDE. Classes, Interfaces, Enums and even new packages can be automatically created by just right clicking in the package you want to create them and choosing `New`.

### Running the PDF Viewer

PDFBox has a PDF file viewer at [**`org.apache.pdfbox.debugger.PDFDebugger.java`**](https://github.com/nilostolte/PDFBox/blob/main/PDFBox-Complete/src/org/apache/pdfbox/debugger/PDFDebugger.java). It is recommended to use this viewer when starting to deal with PDF files. In this viewer one can verify the internal structure, and visualize the contents of the PDF file in several formats, besides just rendering it. To execute this program while in Eclipse, just click at `PDFDebugger.java` and then click over the _play_ icon as indicated below:

![image](https://user-images.githubusercontent.com/80269251/114735122-e0b71880-9d12-11eb-826f-43e53e2acc89.png)

<br>

Once the program opens, one needs to supply a PDF file by using the menu **"File > Open..."**, which just opens a standard window to browse the file wanted. Once the file is loaded it appears in this way:

<br>

![image](https://user-images.githubusercontent.com/80269251/114737155-bbc3a500-9d14-11eb-852f-d66571903d9f.png)

<br>

But the main feature of this viewer is the capability to visualize the real content of the PDF file. This is done by clicking at **"+"** on the left of the **Page** wanted and then clicking at **Contents**.

![image](https://user-images.githubusercontent.com/80269251/114754550-b1aaa200-9d26-11eb-885e-7116b88c4f5f.png)


### Running the Examples

The examples are found in the package [**`org.apache.pdfbox.examples`**](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete/src/org/apache/pdfbox/examples). 

#### Using Fonts

When generating a PDF file from scratch, which contains text, the recommended example is [**`ShowTextWithPositioning.java`**](https://github.com/nilostolte/PDFBox/blob/main/PDFBox-Complete/src/org/apache/pdfbox/examples/pdmodel/ShowTextWithPositioning.java). 
When using fonts, this example shows how to embed them in the PDF file. One can use either a font using an encoding vector (as seen with `PDTrueTypeFont.load`) or directly with the **GID** using **Type 0** fonts (as seen with `PDType0Font.load`). **Type 0** fonts are more convenient because one doesn't have to think about an encoding vector, and it can deal with UNICODE directly. The inconvenient is that each character in a string is stored in two bytes instead of just one byte in the PDF file. 
With texts in English there can be high redundancy of `null` bytes in the first byte of the character which can be compacted using compression. Therefore, using compression, the use of **Type 0** fonts is almost unoticiable in the size of the file. However one looses a convenient feature demonstrated in the [**`ShowTextWithPositioning.java`**](https://github.com/nilostolte/PDFBox/blob/main/PDFBox-Complete/src/org/apache/pdfbox/examples/pdmodel/ShowTextWithPositioning.java), which is the use of **word spacing**.

In this example, texts are positioned using a matrix. 
However, this is quite cumbersome. If one needs to separate the next text
with a custom space, it is better to use the `Td` command. The `x` component of the `Td` command is just the space between the begining of the text before and the one that follows the `Td`, that is, it just translates to a distace x from the beginning of the previous text. The y component of `Td` command is just a zero, when translating in the same line. In PDFBox this command is generated when calling the function [`newLineAtOffset`](https://github.com/nilostolte/PDFBox/blob/a0618eb3c49c0ed660d1e16c5cf090bb96dc5a3c/PDFBox-Complete/src/org/apache/pdfbox/pdmodel/PDPageContentStream.java#L580) from the class [`PDPageContentStream`](https://github.com/nilostolte/PDFBox/blob/main/PDFBox-Complete/src/org/apache/pdfbox/pdmodel/PDPageContentStream.java).

##### GID

**GID** is the **Glyph** identification number. If you are not sure what **GID** means you should download [Glyph Inspector](https://opentype.js.org/glyph-inspector.html) and [opentype.js](https://opentype.js.org/dist/opentype.js), placing `glyph-inspector.html` in some directory and putting `opentype.js` into the same directory under the subdirectory `dist`. In other words, if you copy `glyph-inspector.html` to the directory `test` your `opentype.js` should be at `test/dist`. Other files to put inside `test/dist` in order to make Glyph Inspector to work properly: [opentype.js.map](https://opentype.js.org/dist/opentype.js.map), [opentype.min.js](https://opentype.js.org/dist/opentype.min.js), [opentype.min.js.map](https://opentype.js.org/dist/opentype.min.js.map), [opentype.module.js](https://opentype.js.org/dist/opentype.module.js) and [opentype.module.js.map](https://opentype.js.org/dist/opentype.module.js.map).

When running the program into a browser you will see the following screen:

![image](https://user-images.githubusercontent.com/80269251/114447261-315a3480-9ba0-11eb-9a75-b6c4bab86d2a.png)

The **GID** is the number from 0 to 99 in the grid showing the glyphs of the font, but as one can easily notice the **GID** can go to much higher values, such as 1293 to this particular font. This value can potentially go until 32767.

## [PDFBox-Complete](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete)

This is a complete unmodifed version of PDFBox that is ready for compilation and execution. It contains all necessary packages, where some of them are normally not included in PDFBox source code (Example: `org.bouncycastle`). If you are not using encryption, you can delete `bouncycastle` or simply not copy it to your project. However, you should also delete the examples that use it in order to compile the source code.
