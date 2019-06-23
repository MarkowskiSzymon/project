package com.example.project.Utils;

import android.location.Location;
import android.util.Log;

import com.example.project.R;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.CardsModel;
import com.example.project.model.LoginModel;
import com.example.project.model.MyListData;
import com.example.project.model.PartnersModel;
import com.example.project.model.HistoryModel;

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
                                            Log.v("testparsera", "Dlugosc number: " + cardsModel.mCardsNumber.size());
                                            cardsModel.addToListCardsNumber(nList3.item(l).getTextContent());
                                        } else if (nList3.item(l).getNodeName().equals("pic")) {
                                            cardsModel.addToListCardsIcon(nList3.item(l).getTextContent());
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

    public List<String> parserLoginXML(Document doc, String name) {
            NodeList dane = doc.getElementsByTagName(name);
            LoginModel loginModel = new LoginModel();
                for (int i = 0; i < dane.getLength(); i++) {
                    NodeList nList1 = dane.item(i).getChildNodes();
                    for (int j = 0; j < nList1.getLength(); j++) {
                        if (nList1.item(j).getNodeName().equals("id")) {
                            loginModel.setId(nList1.item(j).getTextContent());
                        } else if (nList1.item(j).getNodeName().equals("imie")) {
                            loginModel.setName(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("telefon")) {
                            loginModel.setPhone(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("email")) {
                            loginModel.setEmail(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("kod_pocztowy")) {
                            loginModel.setZip_code(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("data_urodzenia")) {
                            loginModel.setDate_of_birth(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("plec")) {
                            loginModel.setGender(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("data_rejestracji")) {
                            loginModel.setDate_of_registration(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("czy_zarejestrowany")) {
                            loginModel.setRegister_status(nList1.item(j).getTextContent());
                        }else if (nList1.item(j).getNodeName().equals("czy_aktywny")) {
                            loginModel.setActivity_status(nList1.item(j).getTextContent());
                        }
                        NodeList nList2 = nList1.item(j).getChildNodes();
                        if (nList2.getLength() > 1) {
                            for (int k = 0; k < nList2.getLength(); k++) {
                                NodeList nList3 = nList2.item(k).getChildNodes();
                                if (nList3.getLength() > 1) {
                                    for (int l = 0; l < nList3.getLength(); l++) {
                                        if (nList3.item(l).getChildNodes().getLength() < 2) {
                                            if (nList3.item(l).getNodeName().equals("nr")) {
                                                loginModel.addToListCardsNumber(nList3.item(l).getTextContent());
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
        Log.v("parser", "jestem w parserPartnersXML");
            NodeList dane = doc.getElementsByTagName(name);
            PartnersModel partnersModel = new PartnersModel();
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
            if(partnersModel.mPartners_Id.isEmpty()) {
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
                                                partnersModel.addToListPartnersId(nList3.item(l).getTextContent());
                                                id = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("wid")) {
                                                partnersModel.addToListPartnersWId(nList3.item(l).getTextContent());
                                                wid = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("nazwa")) {
                                                partnersModel.addToListPartnersName(nList3.item(l).getTextContent());
                                                nazwa = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("alt")) {
                                                partnersModel.addToListPartnersLongitude(nList3.item(l).getTextContent());
                                                alt = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("lat")) {
                                                partnersModel.addToListPartnersLatitude(nList3.item(l).getTextContent());
                                                lat = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("opis")) {
                                                partnersModel.addToListPartnersDescription(nList3.item(l).getTextContent());
                                                opis = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("pic")) {
                                                partnersModel.addToListPartnersPicture(nList3.item(l).getTextContent());
                                                pic = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("miasto")) {
                                                partnersModel.addToListPartnersCity(nList3.item(l).getTextContent());
                                                miasto = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("przelicznik")) {
                                                partnersModel.addToListPartnersMultiplier(nList3.item(l).getTextContent());
                                                przelicznik = nList3.item(l).getTextContent();
                                            } else if (nList3.item(l).getNodeName().equals("ilosc_pkt")) {
                                                partnersModel.addToListPartnersOwnedPoints(nList3.item(l).getTextContent());
                                                ilosc_pkt = nList3.item(l).getTextContent();
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


    public List<String> parserPartnersXMLCorreect(Document doc, String name) {
        Log.v("parser", "jestem w parserPartnersXML");
        NodeList dane = doc.getElementsByTagName(name);
        MyListData myListData = new MyListData();
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
        if(myListData.exampleList.isEmpty()) {
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
                                    }
                                }


                                locationA.setLatitude(Double.parseDouble(StartActivity.latitude));
                                locationA.setLongitude(Double.parseDouble(StartActivity.longitude));


                                locationB.setLatitude(Double.parseDouble(lat));
                                locationB.setLongitude(Double.parseDouble(alt));

                                float distance = locationA.distanceTo(locationB)/1000;
                                DecimalFormat f = new DecimalFormat("0.0");


                                myListData.addToExampleList(id, wid, nazwa, alt, lat, opis, pic, miasto, przelicznik, ilosc_pkt, f.format(distance));
                            }
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
        HistoryModel historyModel = new HistoryModel();
        if(historyModel.mTransactionData.isEmpty()){
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
                                            Log.v("testparsera", "Dlugosc data: " + historyModel.mTransactionData.size());
                                            historyModel.addToListTransactionData(nList3.item(l).getTextContent());
                                        }else if (nList3.item(l).getNodeName().equals("typ_transakcji_id")){
                                            historyModel.addToListTransactionType(nList3.item(l).getTextContent());
                                        }else if (nList3.item(l).getNodeName().equals("koszt_punktow")){
                                            historyModel.addToListTransactionExpense(nList3.item(l).getTextContent());
                                        }else if (nList3.item(l).getNodeName().equals("kwota_zakupow")){
                                            historyModel.addToListTransactionAmount(nList3.item(l).getTextContent());
                                        }else if (nList3.item(l).getNodeName().equals("nr_karty")){
                                            historyModel.addToListTransactionCardNumber(nList3.item(l).getTextContent());
                                        }else if (nList3.item(l).getNodeName().equals("pic")){
                                            historyModel.addToListTransactionPicture(nList3.item(l).getTextContent());
                                        }else if (nList3.item(l).getNodeName().equals("nazwa")){
                                            historyModel.addToListTransactionName(nList3.item(l).getTextContent());
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

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////