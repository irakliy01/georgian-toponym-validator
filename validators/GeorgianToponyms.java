package org.openstreetmap.josm.data.validation.tests;

import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Relation;
import org.openstreetmap.josm.data.validation.Severity;
import org.openstreetmap.josm.data.validation.Test;
import org.openstreetmap.josm.data.validation.TestError;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.openstreetmap.josm.tools.I18n.marktr;
import static org.openstreetmap.josm.tools.I18n.tr;

public class GeorgianToponyms extends Test {

    protected static final int WRONG_NAME_KA = 4300;
    protected static final int WRONG_NAME_EN = 4301;

    // Data from https://geonetwork.napr.gov.ge/geonetwork/srv/eng/catalog.search;jsessionid=1E5BD4EC344515648111D65D8073F038#/metadata/be16a17f-96b8-43bf-a25d-bc000cb5d52d
    private final List<String> officialGeorgianTownNamesEn = Arrays.asList(
            "Lentekhi",
            "Chiatura",
            "Zestaponi",
            "Vale",
            "Ninotsminda",
            "Akhalgori",
            "Adigeni",
            "Tianeti",
            "Java",
            "Chokhatauri",
            "Stepantsminda",
            "Keda",
            "Aspindza",
            "Kharagauli",
            "Bakhmaro",
            "Mestia",
            "Khobi",
            "Martvili",
            "Gudauta",
            "Dedoplistskaro",
            "Lanchkhuti",
            "Kobuleti",
            "Ozurgeti",
            "Marneuli",
            "Khoni",
            "Terjola",
            "Kvareli",
            "Kutaisi",
            "Vani",
            "Rustavi",
            "Mtskheta",
            "Samtredia",
            "Khulo",
            "Tsageri",
            "Khashuri",
            "Shuakhevi",
            "Gali",
            "Ambrolauri",
            "Gagra",
            "Gardabani",
            "Senaki",
            "Telavi",
            "Zugdidi",
            "Poti",
            "Akhmeta",
            "Nasakirali",
            "Zhinvali",
            "Surami",
            "Tsnori",
            "Gulripshi",
            "Sighnaghi",
            "Gurjaani",
            "Gori",
            "Abasha",
            "Bolnisi",
            "Sagarejo",
            "Tkibuli",
            "Dusheti",
            "Jvari",
            "Chkhorotsku",
            "Lagodekhi",
            "Sachkhere",
            "Bakuriani",
            "Abastumani",
            "Chakvi",
            "Tsaghveri",
            "Borjomi",
            "Sioni",
            "Bediani",
            "Trialeti",
            "Kulashi",
            "Shorapani",
            "Pasanauri",
            "Tbilisi",
            "Batumi",
            "Sokhumi",
            "Tskhinvali",
            "Manglisi",
            "Tkvarcheli",
            "Ochkhamuri",
            "Agara",
            "Naruja",
            "Khramhesi",
            "Kazreti",
            "Laituri",
            "Kareli",
            "Tskaltubo",
            "Baghdati",
            "Tsalka",
            "Tsalenjikha",
            "Dmanisi",
            "Akhalkalaki",
            "Tamarisi",
            "Akhaltsikhe",
            "Akhaldaba",
            "Oni",
            "Ochamchire",
            "Kaspi",
            "Tetritskaro"
    );
    private final List<String> officialGeorgianTownNamesKa = Arrays.asList(
            "ლენტეხი",
            "ჭიათურა",
            "ზესტაფონი",
            "ვალე",
            "ნინოწმინდა",
            "ახალგორი",
            "ადიგენი",
            "თიანეთი",
            "ჯავა",
            "ჩოხატაური",
            "სტეფანწმინდა",
            "ქედა",
            "ასპინძა",
            "ხარაგაული",
            "ბახმარო",
            "მესტია",
            "ხობი",
            "მარტვილი",
            "გუდაუთა",
            "დედოფლისწყარო",
            "ლანჩხუთი",
            "ქობულეთი",
            "ოზურგეთი",
            "მარნეული",
            "ხონი",
            "თერჯოლა",
            "ყვარელი",
            "ქუთაისი",
            "ვანი",
            "რუსთავი",
            "მცხეთა",
            "სამტრედია",
            "ხულო",
            "ცაგერი",
            "ხაშური",
            "შუახევი",
            "გალი",
            "ამბროლაური",
            "გაგრა",
            "გარდაბანი",
            "სენაკი",
            "თელავი",
            "ზუგდიდი",
            "ფოთი",
            "ახმეტა",
            "ნასაკირალი",
            "ჟინვალი",
            "სურამი",
            "წნორი",
            "გულრიფში",
            "სიღნაღი",
            "გურჯაანი",
            "გორი",
            "აბაშა",
            "ბოლნისი",
            "საგარეჯო",
            "ტყიბული",
            "დუშეთი",
            "ჯვარი",
            "ჩხოროწყუ",
            "ლაგოდეხი",
            "საჩხერე",
            "ბაკურიანი",
            "აბასთუმანი",
            "ჩაქვი",
            "წაღვერი",
            "ბორჯომი",
            "სიონი",
            "ბედიანი",
            "თრიალეთი",
            "კულაში",
            "შორაპანი",
            "ფასანაური",
            "თბილისი",
            "ბათუმი",
            "სოხუმი",
            "ცხინვალი",
            "მანგლისი",
            "ტყვარჩელი",
            "ოჩხამური",
            "აგარა",
            "ნარუჯა",
            "ხრამჰესი",
            "კაზრეთი",
            "ლაითური",
            "ქარელი",
            "წყალტუბო",
            "ბაღდათი",
            "წალკა",
            "წალენჯიხა",
            "დმანისი",
            "ახალქალაქი",
            "თამარისი",
            "ახალციხე",
            "ახალდაბა",
            "ონი",
            "ოჩამჩირე",
            "კასპი",
            "თეთრიწყარო"
    );

    public GeorgianToponyms() {
        super(tr("Georgian Toponym Validator"), tr("Checks whether city/town name is an official Georgian name."));
    }

    @Override
    public void visit(Node node) {
        if ((node.hasTag("place", "city") || node.hasTag("place", "town")) && isNodeInGeorgia(node)) {
            String nameTagValueKa = node.get("name:ka");
            String nameTagValueEn = node.get("name:en");

            if (nameTagValueKa != null && !isValidGeorgianNameKa(nameTagValueKa)) {
                errors.add(TestError.builder(this, Severity.WARNING, WRONG_NAME_KA)
                        .message(tr("Invalid Georgian name"), marktr("Name:ka " + nameTagValueKa + " does not seem to be an official Georgian city/town name."), node)
                        .primitives(node)
                        .build());
            }
            if (nameTagValueEn != null && !isValidGeorgianNameEn(nameTagValueEn)) {
                errors.add(TestError.builder(this, Severity.WARNING, WRONG_NAME_EN)
                        .message(tr("Invalid Georgian name"), marktr("Name:en " + nameTagValueEn + " does not seem to be an official Georgian city/town name."), node)
                        .primitives(node)
                        .build());
            }
        }
    }

    private boolean isValidGeorgianNameKa(String name) {
        return officialGeorgianTownNamesKa.contains(name);
    }

    private boolean isValidGeorgianNameEn(String name) {
        return officialGeorgianTownNamesEn.contains(name);
    }

    private boolean isNodeInGeorgia(Node node) {
        return checkReferrersRecursively(node, new HashSet<>());
    }

    private boolean checkReferrersRecursively(OsmPrimitive current, Set<Long> visited) {
        if (visited.contains(current.getUniqueId())) {
            return false;
        }
        visited.add(current.getUniqueId());

        if (current instanceof Relation) {
            Relation relation = (Relation) current;
            if ("2".equals(relation.get("admin_level")) && "Georgia".equals(relation.get("int_name"))) {
                return true;
            }
        }


        List<Relation> referrers = current.referrers(Relation.class).collect(Collectors.toList());
        for (Relation referrer : referrers) {
            if (checkReferrersRecursively(referrer, visited)) {
                return true;
            }
        }

        return false;
    }
}