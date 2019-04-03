package data;

import java.math.BigInteger;

public class Util {
    public static CQTemplate createTemplate(String template) {

        if (template != null) {
            template = "";
        }

        CQTemplate result = new CQTemplate();

        String[] words = template.replaceAll(",", " ,").replaceAll("\\?", " ?").split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (int i=0; i<words.length; i++) {
            String currentWord = words[i];
            if (currentWord.startsWith("[") || currentWord.startsWith("?")) {

                //getting last string
                String lastphrase = stringBuilder.toString();
                if (!lastphrase.isEmpty()) {
                    TextChunk textChunk = new TextChunk();
                    textChunk.setID(BigInteger.valueOf(count));
                    textChunk.setValue(lastphrase.trim());
                    result.addTextOrPredicateChunk(CQXMLFileHandler.xmlObjectCreator.createTextChunk(textChunk));
                    //removing contents of string builder
                    stringBuilder.setLength(0);
                }

                //getting current string
                if (currentWord.startsWith("[EC")) {
                    EntityChunk entityChunk = new EntityChunk();
                    entityChunk.setID(BigInteger.valueOf(count));
                    entityChunk.setValue(currentWord);
                    count += 1;
                    result.addEntityChunk(CQXMLFileHandler.xmlObjectCreator.createEntityChunk(entityChunk));
                } else if (currentWord.startsWith("[PC")) {
                    PredicateChunk entityChunk = new PredicateChunk();
                    entityChunk.setID(BigInteger.valueOf(count));
                    entityChunk.setValue(currentWord);
                    count += 1;
                    result.addTextOrPredicateChunk(CQXMLFileHandler.xmlObjectCreator.createPredicateChunk(entityChunk));
                } else if (currentWord.startsWith("?")) {
                    TextChunk textChunk = new TextChunk();
                    textChunk.setID(BigInteger.valueOf(count));
                    textChunk.setValue(currentWord);
                    result.addTextOrPredicateChunk(CQXMLFileHandler.xmlObjectCreator.createTextChunk(textChunk));
                }

            } else {
                stringBuilder.append(" ");
                stringBuilder.append(currentWord);
            }
        }

        return result;
    }

}
