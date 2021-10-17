package plenix.tools.xmlite

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

def load(path: String) =
  Source.fromURL(getClass.getResource("/" + path))
    .getLines()
    .reduce(_ + "\n" + _)
    .split("\n?================================================================\\d*\n?")
    .drop(1)
    .grouped(2)
    .map(x => (x(0), x(1)))
    .toList

def parse(s: String) =
  val parser = new xml.parsing.ConstructingParser(Source.fromString(s), true) :
    override def replacementText(entityName: String): io.Source = super.replacementText(entityName)

    nextch()

  parser.element(xml.TopScope)

def test(data: (String, String)) =
  val (input, output) = data
  val expectedOutput = parse("<root>\n" + output + "\n</root>")
  val parsedOutput = parse(DefaultXMLiteParser.parse(input).toString())
  assert(parse(parsedOutput.toString) == parse(expectedOutput.toString))

class BlockTests extends AnyFunSuite :
  val data = load("blocks.txt")
  test("autoblock of plain text") {
    data(0)
  }
  test("Single-line block with single autoblock") {
    data(1)
  }
  test("simple multiline block") {
    data(2)
  }
  test("long multiline block with a blank line") {
    data(3)
  }
  test("multiline block with text on first line") {
    data(4)
  }
  test("multiline block with text after") {
    data(5)
  }
  test("complex series of nested blocks") {
    data(6)
  }
  test("escaping of block delimiters with backslashes") {
    data(7)
  }
  test("escaping of backslashes") {
    data(8)
  }
  test("multi-tag block") {
    data(9)
  }

class EntityTests extends AnyFunSuite :
  val data = load("entities.txt")
  test("basic entity works and html-style entity fails") {
    data(0)
  }
  test("multiple entities chained together") {
    data(1)
  }
  test("numeric entities") {
    data(2)
  }
  test("ensuring entities can't contain spaces") {
    data(3)
  }
  test("entities inside an inline tag") {
    data(4)
  }
  test("escaping entities") {
    data(5)
  }

class InlineTests extends AnyFunSuite :
  val data = load("inline.txt")
  test("simple inline tags") {
    data(0)
  }
  test("inline tags spread over multiple lines") {
    data(1)
  }
  test("inline tags within a block tag") {
    data(2)
  }
  test("complex inline tags inside block tags") {
    data(3)
  }
  test("escaping of inline tags") {
    data(4)
  }
  test("more escaping of inline tags") {
    data(5)
  }
  test("inline tags in the middle of a word") {
    data(6)
  }
  test("inline tags inside another inline") {
    data(7)
  }

class AttributeTests extends AnyFunSuite :
  val data = load("attributes.txt")
  test("simple attributes") {
    data(0)
  }
  test("attributes on a block tag") {
    data(1)
  }
  test("auto-numbered nameless attributes") {
    data(2)
  }
  test("attributes on inline tags") {
    data(3)
  }
  test("spreading attributes over multiple lines") {
    data(4)
  }
  test("escaping delimiters inside attributes") {
    data(5)
  }
  test("attributes on separate lines") {
    data(6)
  }

class ProcessingInstructionTests extends AnyFunSuite :
  val data = load("procinsts.txt")
  test("singleline block procinsts") {
    data(0)
  }
  test("inline procinsts") {
    data(1)
  }
  test("both singleline block and inline procinsts") {
    data(2)
  }
  test("multiline block procinsts") {
    data(3)
  }
  test("multiply indented multiline block procinsts") {
    data(4)
  }
  test("escaping procinsts") {
    data(5)
  }

class RawBlockTests extends AnyFunSuite :
  val data = load("rawblocks.txt")
  test("simple multiline raw block") {
    data(0)
  }
  test("more complex raw block, ensuring nothing gets parsed") {
    data(1)
  }
  test("multi-tag raw blocks") {
    data(2)
  }

class ListTests extends AnyFunSuite :
  val data = load("lists.txt")
  test("short list with one item each, autochunked") {
    data(0)
  }
  test("short list nested in a tag") {
    data(1)
  }
  test("lists nested within each other") {
    data(2)
  }
  test("numbered lists with nesting") {
    data(3)
  }
  test("lists with blank lines in between") {
    data(4)
  }
  test("escaping lists") {
    data(5)
  }

class ShortcutTests extends AnyFunSuite :
  val data = load("shortcuts.txt")
  test("single bold word") {
    data(0)
  }
  test("entire range of shortcuts") {
    data(1)
  }
  test("shortcuts over multiple lines but not multiple blocks") {
    data(2)
  }
  test("reStructuredText shortcut behavior") {
    data(3)
  }
  test("shortcuts inside a long word") {
    data(4)
  }
  test("more shortcuts inside a long word") {
    data(5)
  }
  test("shortcuts immediately inside inline block - left") {
    data(6)
  }
  test("shortcuts immediately inside inline block - right") {
    data(7)
  }
  test("shortcuts immediately inside inline block - tight") {
    data(8)
  }
  test("shortcuts immediately inside inline block - open") {
    data(9)
  }
  test("shortcuts within shortcuts") {
    data(10)
  }

class AutoChunkTests extends AnyFunSuite :
  val data = load("autochunk.txt")
  test("several paragraphs being chunked") {
    data(0)
  }
  test("chunked paragraph with explicit multiline block paragraph") {
    data(1)
  }
  test("auto-chunking inside divs") {
    data(2)
  }
  test("nested autochunking divs") {
    data(3)
  }
  test("autochunking lists") {
    data(4)
  }
  test("escaping autochunks") {
    data(5)
  }
  test("bold starting autochunk") {
    data(6)
  }

class SampleTests extends AnyFunSuite :
  val data = load("samples.txt")
  test("dropbox developer reference") {
    data(0)
  }
  test("wikipedia snippet") {
    data(1)
  }
  test("simple Ant build.xml file") {
    data(2)
  }
  test("microsoft excel spreadsheet xml") {
    data(3)
  }
