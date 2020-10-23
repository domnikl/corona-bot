# corona-bot

This is a little Discord bot, that will post Corona reports from Robert-Koch-Institut data. 

## Configuration

```properties
discord {
    token = <your-discord-bot-token>
    channel = general
}

robertKochDataSource {
    url = "https://opendata.arcgis.com/datasets/917fc37a709542548cc3be077a786c17_0.geojson"
    regions = ["Main-Spessart", "Würzburg"]
}
```
