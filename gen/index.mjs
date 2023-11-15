import { PackLoader, createLogger } from "@pssbletrngle/data-modifier";
import { Mergers } from "@pssbletrngle/resource-merger";

const logger = createLogger();

async function run() {
  const output = new Mergers(
    { output: "../datapacks/generated", overwrite: true },
    {}
  );

  const loader = new PackLoader(logger);

  loader.content.items.blockItem("example:small_lapis_cog", {
    type: "create:cog",
    block: (blocks) => blocks.cog({ material: "metal", strength: 2.0 }),
  });

  loader.content.items.blockItem("example:large_lapis_cog", {
    type: "create:cog",
    block: (blocks) =>
      blocks.cog({ material: "metal", strength: 2.0, large: true }),
  });

  logger.info("generating modified resources...");
  await loader.emit(output.createAcceptor());
  await output.finalize();
}

run().catch((e) => logger.error(e.message));
