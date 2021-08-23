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

## Using it

First download the fat jar from the [releases](https://github.com/domnikl/corona-bot/releases) page. Then you need to 
configure the following parameters and put them in `corona-bot.conf`. You can create a new bot on https://discord.com/developers/applications/.

```properties
discord {
    token = <your-discord-bot-token>
    channel = <a-text-channel-on-your-server>
}

robertKochDataSource {
    url = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=GEN,cases,deaths,county,last_update,cases7_per_100k&outSR=4326&f=json"
    regions = ["Main-Spessart", "Würzburg"]
}
```

Then run it like this: `java -jar corona-bot.jar corona-bot.conf`. As the data can get really big and data will only
be published at 00:00 local time every day, I would advise you to run this in a daily cronjob.
