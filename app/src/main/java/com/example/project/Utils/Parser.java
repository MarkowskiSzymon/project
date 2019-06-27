package com.example.project.Utils;

import android.location.Location;
import android.util.Log;

import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.CardModelTest;
import com.example.project.model.CardsModel;
import com.example.project.model.LoginModelTest;
import com.example.project.model.PartnersModel;
import com.example.project.model.RewardsModel;
import com.example.project.model.TransactionsModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Parser {
    public ArrayList<String> lista = new ArrayList<>();

    public Document getDocument(String response) {
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response)));
        } catch (IOException e) {
            Log.v("App", "Err" + e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.v("App", "Err" + e.getMessage());
        } catch (SAXException e) {
            Log.v("App", "Err" + e.getMessage());
        }
        return doc;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    <dane>
        <xf>99</xf>
    </dane>
*/


    public String parserXML(Document document, String name) {
        NodeList dane = document.getElementsByTagName("dane");
        String ret = null;
        for (int i = 0; i < dane.getLength(); i++) {
            Node nNode = dane.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                ret = eElement.getElementsByTagName(name).item(0).getTextContent();
            }
        }

        return ret;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    <dane>
        <xl>
             <elem>Poprawne logowanie</elem>
        </xl>
    </dane>
    */

    public String[] parserXMLArray(Document document, String name, String name2) {
        NodeList dane = document.getElementsByTagName("dane");
        String[] ret = null;
        for (int i = 0; i < dane.getLength(); i++) {
            Element eElement = (Element) dane.item(i);
            NodeList nodeList = eElement.getElementsByTagName(name2);
            ret = new String[nodeList.getLength()];
            for (int j = 0; j < nodeList.getLength(); j++) {

                ret[j] = getValue(eElement, "elem", j);
            }
        }
        return ret;
    }

    public String getValue(Element item, String str, int i) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(i));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /*
    <dane>
        <xd>
            <elem>
                <nr_karty>
                    <nr>...</nr>
                    <pic>...</pic>
                </nr_karty>
                <nr_karty>
                    <nr>...</nr>
                    <pic>...</pic>
                </nr_karty>
            </elem>
        </xd>
    </dane>
*/

    public List<String> parserCardsXML(Document doc, String name) {
        NodeList dane = doc.getElementsByTagName(name);
        CardsModel cardsModel = new CardsModel();
        String nr = null;
        String pic = null;
        if(cardsModel.mCardsNumber.isEmpty()){
            for (int i = 0; i < dane.getLength(); i++) {
                NodeList nList1 = dane.item(i).getChildNodes();
                for (int j = 0; j < nList1.getLength(); j++) {
                    NodeList nList2 = nList1.item(j).getChildNodes();
                    if (nList2.getLength() > 1) {
                        for (int k = 0; k < nList2.getLength(); k++) {
                            NodeList nList3 = nList2.item(k).getChildNodes();
                            if (nList3.getLength() > 1) {
                                for (int l = 0; l < nList3.getLength(); l++) {
                                    if (nList3.item(l).getChildNodes().getLength() < 2) {
                                        if (nList3.item(l).getNodeName().equals("nr")) {
                                            nr = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("pic")) {
                                            pic = nList3.item(l).getTextContent();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public List<String> parserLoginXML(Document doc, String name) {
        NodeList dane = doc.getElementsByTagName(name);
        LoginModelTest loginModelTest = new LoginModelTest();
        CardModelTest cardModelTest = new CardModelTest();
        String id = null;
        String imie = null;
        String telefon = null;
        String email = null;
        String kod_pocztowy = null;
        String data_urodzenia = null;
        String plec = null;
        String data_rejestracji = null;
        String czy_zarejestrowany = null;
        String czy_aktywny = null;
        String nr = null;
        String pic = null;
        for (int i = 0; i < dane.getLength(); i++) {
            NodeList nList1 = dane.item(i).getChildNodes();
            for (int j = 0; j < nList1.getLength(); j++) {
                if (nList1.item(j).getNodeName().equals("id")) {
                    id = nList1.item(j).getTextContent();
                } else if (nList1.item(j).getNodeName().equals("imie")) {
                    imie = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("telefon")) {
                    telefon = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("email")) {
                    email = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("kod_pocztowy")) {
                    kod_pocztowy = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("data_urodzenia")) {
                    data_urodzenia = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("plec")) {
                    plec = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("data_rejestracji")) {
                    data_rejestracji = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("czy_zarejestrowany")) {
                    czy_zarejestrowany = nList1.item(j).getTextContent();
                }else if (nList1.item(j).getNodeName().equals("czy_aktywny")) {
                    czy_aktywny = nList1.item(j).getTextContent();
                } NodeList nList2 = nList1.item(j).getChildNodes();
                if (nList2.getLength() > 1) {
                    for (int k = 0; k < nList2.getLength(); k++) {
                        NodeList nList3 = nList2.item(k).getChildNodes();
                        if (nList3.getLength() > 1) {
                            for (int l = 0; l < nList3.getLength(); l++) {
                                if (nList3.item(l).getChildNodes().getLength() < 2) {
                                    if (nList3.item(l).getNodeName().equals("nr")) {
                                        nr = nList3.item(l).getTextContent();
                                    } else if (nList3.item(l).getNodeName().equals("pic")) {
                                        pic = nList3.item(l).getTextContent();
                                    }
                                }
                            }
                        }
                        cardModelTest.addToCardList(nr, pic);
                    }
                }
                loginModelTest.addToInformationList(id, imie, telefon, email, kod_pocztowy, data_urodzenia, plec, data_rejestracji, czy_zarejestrowany, czy_aktywny, cardModelTest.listOfCards);
            }
        }
        return null;
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /*
 <dane>
        <xd>
            <id>...</id>
            <imie>...</imie>
            <telefon>...</telefon>
            <email>...</email>
            <kod_pocztowy>...</kod_pocztowy>
            <data_urodzenia>...</data_urodzenia>
            <plec>...</plec>
            <data_rejestracji>...</data_rejestracji>
            <czy_zarejestrowany>...</czy_zarejestrowany>
            <czy_aktywny>...</czy_aktywny>
        </xd>
</dane>
*/






    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    <dane>
        <xl>
            <elem>...</elem>
        </xl>
    </dane>
*/

    public String parserSimpleXML(Document doc, String name) {
        NodeList dane = doc.getElementsByTagName(name);
        String result = null;
        for (int i = 0; i < dane.getLength(); i++) {
            NodeList nList1 = dane.item(i).getChildNodes();
            for (int j = 0; j < nList1.getLength(); j++) {
                NodeList nList2 = nList1.item(j).getChildNodes();
                if (nList1.item(j).getChildNodes().getLength() < 2 ) {
                    result = nList1.item(j).getTextContent();
                }

            }
        }
        return result;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public List<String> parserPartnersXML(Document doc, String name) {
        NodeList dane = doc.getElementsByTagName(name);
        PartnersModel myListData = new PartnersModel();
        RewardsModel rewardsModel = new RewardsModel();
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");
        String id = null;
        String wid = null;
        String nazwa = null;
        String alt = null;
        String lat = null;
        String opis = null;
        String pic = null;
        String miasto = null;
        String przelicznik = null;
        String ilosc_pkt = null;
        String nazwaPromocji = null;
        String iloscPunktowPromocji = null;
        if(myListData.listOfPartners.isEmpty()) {
            for (int i = 0; i < dane.getLength(); i++) {
                NodeList nList1 = dane.item(i).getChildNodes();
                for (int j = 0; j < nList1.getLength(); j++) {
                    NodeList nList2 = nList1.item(j).getChildNodes();
                    if (nList2.getLength() > 1) {
                        for (int k = 0; k < nList2.getLength(); k++) {
                            NodeList nList3 = nList2.item(k).getChildNodes();
                            if (nList3.getLength() > 1) {
                                for (int l = 0; l < nList3.getLength(); l++) {
                                    if (nList3.item(l).getChildNodes().getLength() < 2) {
                                        if (nList3.item(l).getNodeName().equals("id")) {
                                            id = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("wid")) {
                                            wid = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("nazwa")) {
                                            nazwa = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("alt")) {
                                            alt = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("lat")) {
                                            lat = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("opis")) {
                                            opis = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("pic")) {
                                            pic = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("miasto")) {
                                            miasto = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("przelicznik")) {
                                            przelicznik = nList3.item(l).getTextContent();
                                        } else if (nList3.item(l).getNodeName().equals("ilosc_pkt")) {
                                            ilosc_pkt = nList3.item(l).getTextContent();
                                        }
                                    }else if(nList3.item(l).getChildNodes().getLength() > 2){
                                        NodeList nList4 = nList3.item(l).getChildNodes();
                                        for (int m = 0; m < nList4.getLength(); m++) {
                                            NodeList nList5 = nList4.item(m).getChildNodes();
                                            if (nList5.getLength() > 1){
                                                for (int n = 0; n < nList5.getLength(); n++) {
                                                    if (nList5.item(n).getChildNodes().getLength() < 2) {
                                                        if (nList5.item(n).getNodeName().equals("nazwa")) {
                                                            nazwaPromocji = nList5.item(n).getTextContent();
                                                        } else if (nList5.item(n).getNodeName().equals("ilosc_pkt")) {
                                                            iloscPunktowPromocji = nList5.item(n).getTextContent();
                                                        }
                                                    }
                                                }
                                            }
                                            rewardsModel.addToPromoList(nazwaPromocji, iloscPunktowPromocji, nList4.getLength());
                                        }
                                    }
                                }
                            }
                            locationA.setLatitude(Double.parseDouble(StartActivity.latitude));
                            locationA.setLongitude(Double.parseDouble(StartActivity.longitude));
                            locationB.setLatitude(Double.parseDouble(lat));
                            locationB.setLongitude(Double.parseDouble(alt));
                            float distance = locationA.distanceTo(locationB) / 1000;
                            DecimalFormat f = new DecimalFormat("0.0");
                            myListData.addToExampleList(id, wid, nazwa, alt, lat, opis, pic, miasto, przelicznik, ilosc_pkt, f.format(distance), rewardsModel.listOfPromo);
                        }
                    }
                }
            }
        }
        return null;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    <dane>
        <xd>
        <elem>
            <trx>
                <data>...</data>
                <typ_transakcji_id>...</typ_transakcji_id>
                <koszt_punktow>...</koszt_punktow>
                <kwota_zakupow>...</kwota_zakupow>
                <nr_karty>...</nr_karty>
                <pic>...</pic>
                <nazwa>...</nazwa>
            </trx>
            <trx>
                <data>...</data>
                <typ_transakcji_id>...</typ_transakcji_id>
                <koszt_punktow>...</koszt_punktow>
                <kwota_zakupow>...</kwota_zakupow>
                <nr_karty>...</nr_karty>
                <pic>...</pic>
                <nazwa>...</nazwa>
            </trx>
            <trx>
                <data>...</data>
                <typ_transakcji_id>...</typ_transakcji_id>
                <koszt_punktow>...</koszt_punktow>
                <kwota_zakupow>...</kwota_zakupow>
                <nr_karty>...</nr_karty>
                <pic>...</pic>
                <nazwa>...</nazwa>
            </trx>
        </elem>
    </xd>
*/

    public List<String> parserTransactionsXML(Document doc, String name) {
        NodeList dane = doc.getElementsByTagName(name);
        TransactionsModel transactionsModel = new TransactionsModel();
        String data = null;
        String typ_transakcji_id = null;
        String koszt_punktow = null;
        String kwota_zakupow = null;
        String nr_karty = null;
        String pic = null;
        String nazwa = null;
        if(transactionsModel.listOfTransactions.isEmpty()) {
            for (int i = 0; i < dane.getLength(); i++) {
                NodeList nList1 = dane.item(i).getChildNodes();
                for (int j = 0; j < nList1.getLength(); j++) {
                    NodeList nList2 = nList1.item(j).getChildNodes();
                    if (nList2.getLength() > 1) {
                        for (int k = 0; k < nList2.getLength(); k++) {
                            NodeList nList3 = nList2.item(k).getChildNodes();
                            if (nList3.getLength() > 1) {
                                for (int l = 0; l < nList3.getLength(); l++) {
                                    if (nList3.item(l).getChildNodes().getLength() < 2) {
                                        if(nList3.item(l).getNodeName().equals("data") ){
                                            data = nList3.item(l).getTextContent();
                                        }else if (nList3.item(l).getNodeName().equals("typ_transakcji_id")){
                                            typ_transakcji_id = nList3.item(l).getTextContent();
                                        }else if (nList3.item(l).getNodeName().equals("koszt_punktow")){
                                            koszt_punktow = nList3.item(l).getTextContent();
                                        }else if (nList3.item(l).getNodeName().equals("kwota_zakupow")){
                                            kwota_zakupow = nList3.item(l).getTextContent();
                                        }else if (nList3.item(l).getNodeName().equals("nr_karty")){
                                            nr_karty = nList3.item(l).getTextContent();
                                        }else if (nList3.item(l).getNodeName().equals("pic")){
                                            pic = nList3.item(l).getTextContent();
                                        }else if (nList3.item(l).getNodeName().equals("nazwa")){
                                            nazwa = nList3.item(l).getTextContent();
                                        }
                                    }
                                }
                                transactionsModel.addToListOfTransactions(data, typ_transakcji_id, koszt_punktow, kwota_zakupow, nr_karty, pic, nazwa);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////