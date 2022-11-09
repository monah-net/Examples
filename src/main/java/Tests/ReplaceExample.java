package Tests;


public class ReplaceExample {
    public static void main(String[] args) {
        String string = "(; Reference to: ModifyModel[this].modifications[\"rpt_acct_info\"{b18d2cd2-969d-494f-a3fc-abfa1117aaea}].dataSource.layout[\"legal_entity_id\"{9aa1140a-81d8-4896-b3ca-c349fda33578}];  is NULL or ; Reference to: ModifyModel[this].modifications[\"rpt_acct_info\"{b18d2cd2-969d-494f-a3fc-abfa1117aaea}].dataSource.layout[\"legal_entity_id\"{9aa1140a-81d8-4896-b3ca-c349fda33578}];  = ; Reference to: DataModel[\"fatca_data_in:Aggregation[rpt_doc_spec_IN]\"{ff6f8199-15fb-409c-8e7e-083695c9bc5a:Aggregation[c5fcef4a-cb91-4f8f-abd0-e354d633be11]}].hierarchy[\"Aggregation\"{c5fcef4a-cb91-4f8f-abd0-e354d633be11}].dataSource.layout[\"legal_entity_id\"{d5072d25-4ad2-41e8-8cf5-9a2fd84a6208}]; ) and";
        String replaced = string.replaceAll("\\(; Reference to: ModifyModel\\[this\\].modifications\\[\""," ").replaceAll("\"\\{.*\\[\"",".").replaceAll("\"\\{.*\\}\\]; \\)","");
        System.out.println(replaced);
    }
}