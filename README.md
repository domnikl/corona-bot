# corona-bot

This is a little Discord bot that will post **Corona** reports in a Discord text channel using data from from Robert-Koch-Institut. 

## Looks

Reports will look something like this:

> :chart_with_upwards_trend:  :green_circle::black_circle::black_circle: Das ist der Corona-Report für heute (2020-10-23) in LK Main-Spessart:
> 30.12 7-Tage-Inzidenz
> 116 aktuell Infizierte
> 6 Todesfälle
>
>
> :chart_with_upwards_trend:  :black_circle::black_circle::red_circle: Das ist der Corona-Report für heute (2020-10-23) in SK Würzburg:
> 89.11 7-Tage-Inzidenz
> 995 aktuell Infizierte
> 52 Todesfälle
>
>
> :chart_with_upwards_trend:  :black_circle::black_circle::red_circle: Das ist der Corona-Report für heute (2020-10-23) in LK Würzburg:
> 65.31 7-Tage-Inzidenz
> 818 aktuell Infizierte
> 7 Todesfälle
>
>
> Bleibt bitte gesund! :sunny:

## Configuration

You need to configure the following parameters, put them in `corona-bot.conf` and provide it as the first argument when running Corona bot like this: `java -jar corona-bot.jar corona-bot.conf`. 

```properties
discord {
    token = <your-discord-bot-token>
    channel = <a-text-channel-on-your-server>
}

robertKochDataSource {
    url = "https://opendata.arcgis.com/datasets/917fc37a709542548cc3be077a786c17_0.geojson"
    regions = ["Main-Spessart", "Würzburg"]
}
```

As the data can get really big and data will only be published at 00:00 local time, I would advise you to run this in a cronjob.
