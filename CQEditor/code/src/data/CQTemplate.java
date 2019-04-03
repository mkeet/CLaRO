//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.04.03 at 12:59:14 PM SAST 
//


package data;

import javafxui.CONFIG;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import javax.xml.soap.Text;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{}TemplateBase">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="EntityChunk" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded"/>
 *         &lt;element ref="{}OptionalChunk" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CQTemplate" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "entityChunkOrOptionalChunkOrCQTemplate"
})
@XmlRootElement(name = "CQTemplate")
public class CQTemplate
    extends TemplateBase
{

    @XmlElementRefs({
        @XmlElementRef(name = "CQTemplate", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "EntityChunk", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OptionalChunk", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> entityChunkOrOptionalChunkOrCQTemplate;

    /**
     * Gets the value of the entityChunkOrOptionalChunkOrCQTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entityChunkOrOptionalChunkOrCQTemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityChunkOrOptionalChunkOrCQTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link OptionalChunk }{@code >}
     * {@link JAXBElement }{@code <}{@link OptionalChunk }{@code >}
     * {@link JAXBElement }{@code <}{@link OptionalChunk }{@code >}
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getEntityChunkOrOptionalChunkOrCQTemplate() {
        if (entityChunkOrOptionalChunkOrCQTemplate == null) {
            entityChunkOrOptionalChunkOrCQTemplate = new ArrayList<JAXBElement<?>>();
        }
        return this.entityChunkOrOptionalChunkOrCQTemplate;
    }

    public void addEntityChunk(JAXBElement<EntityChunk> entityChunk) {
        entityChunkOrOptionalChunkOrCQTemplate.add(entityChunk);
    }

    public void addTextOrPredicateChunk(JAXBElement<OptionalChunk> textOrPredicateChunk) {
        entityChunkOrOptionalChunkOrCQTemplate.add(textOrPredicateChunk);
    }

    public void addCQTemplate(JAXBElement<CQTemplate> cqTemplate) {
        entityChunkOrOptionalChunkOrCQTemplate.add(cqTemplate);
    }

    @Override
    public String toString() {
        String result = "";

        if (entityChunkOrOptionalChunkOrCQTemplate != null) {
            for (JAXBElement<?> element : entityChunkOrOptionalChunkOrCQTemplate) {
                Object currentItem = element.getValue();
                QName chunkType = element.getName();
                if (chunkType.toString().equals("TextChunk")) {
                    result += " " + ((OptionalChunk)currentItem).getValue();
                } else if (chunkType.toString().equals("PredicateChunk")) {
                    result += " " + CONFIG.VERB_PHRASE_PLACEHOLDER;
                } else if (chunkType.toString().equals("EntityChunk")) {
                    result += " " + CONFIG.NOUN_PHRASE_PLACEHOLDER;
                }
            }
            result += '?';
        }

        return result.trim();
    }
}
