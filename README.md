# PDFBox

The Apache PDFBox library is an open source Java tool for working with PDF documents. The Apache PDFBox library project allows creation of new PDF documents, manipulation of existing documents and the ability to extract content from documents. Apache PDFBox also includes several command-line utilities.

The project in this repository offers several versions of [PDFBox](https://pdfbox.apache.org) **source code** that can be directly compiled with Eclipse without using Maven. The source code version used here is `pdfbox-2.0.23`. [The complete version](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete) (**PDFBox-Complete**) is a complete unmodified PDFBox with all packages. The other versions, which are in other repositories for convenience, are modified versions offering more capabilities and generally for more specific uses.

The advantage of these repositories is that no building software is necessary. One can compile them using Eclipse for Java, for example. To do that, just create a new Java project in Eclipse and then copy all the files under the directory **`src`**

## [PDFBox-Complete](https://github.com/nilostolte/PDFBox/tree/main/PDFBox-Complete)

This is a complete unmodifed version of PDFBox that is ready for compilation and execution. It contains all necessary packages, where some of them are normally not included in PDFBox source code (Example: `org.bouncycastle`). If you are not using encryption, you can delete `bouncycastle` or simply not copy it to your project. However, you should also delete the examples that use it in order to compile the source code.
